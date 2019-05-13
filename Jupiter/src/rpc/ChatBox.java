package rpc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import javabeans.Appointment;
//import repositories.AppointmentsRepository;
//import repositories.SearchConncet;

@WebServlet(name = "ChatBox", urlPatterns = {"/chatbox"})
public class ChatBox extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
    	
    	HttpSession session=request.getSession();
    	String user_role=session==null ? "":(String)session.getAttribute("role");
		user_role=user_role==null? "":user_role;
		switch (user_role){
			case "":
				response.sendRedirect("login");  
				return;
		}
		
		String chatHistory=(String)request.getParameter("chatArea");
		String newChat=(String)request.getParameter("userInput");
		String answer="Bot: "+answerQuestion((String)session.getAttribute("username"), user_role, newChat)+"\n";
		String[] chat=chatHistory.split("_");
		chatHistory=String.join("\n", chat)+"\n";
		//either split with new lines here, or split w/ new lines on textarea on html
    	session.setAttribute("chat", chatHistory+answer);
    	response.getWriter().print(answer);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
    	
    }
    
    //will likely go somewhere else
    String answerQuestion(String userId, String role, String line) {
    	if(line.contains("When is my next appointment?")) {
    		List<Appointment> appts= AppointmentsRepository.getTodaysAppointments(role, userId);
    		if(appts.isEmpty()) {
    			return "You have no more appointments today.";    			
    		}
    		return new SimpleDateFormat("MMMMM dd, YYYY hh:mm a").format(appts.get(0).getStart());
    	}
    	else if(line.contains("Who is my next appointment?")) {
    		List<Appointment> appts= AppointmentsRepository.getTodaysAppointments(role, userId);
    		if(appts.isEmpty()) {
    			return "You have no more appointments today.";    			
    		}
    		return appts.get(0).getName();
    		
    	}
    	else if(line.contains("How many appointments do I have left today?")) {
    		return AppointmentsRepository.getTodaysAppointments(role, userId).size()+"";
    	}
    	else if(line.contains("What is my availability")) {
    		return SearchConncet.getAvailability(userId, line.substring(2+line.indexOf(":", 3)));
    	}
    	return "I'm sorry. I did not understnad your question.";
    }
    
    
}