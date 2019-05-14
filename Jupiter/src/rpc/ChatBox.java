package rpc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import recommendation.GeoRecommendation;

//import javabeans.Appointment;
//import repositories.AppointmentsRepository;
//import repositories.SearchConncet;

@WebServlet(name = "ChatBox", urlPatterns = {"/chatbox"})
public class ChatBox extends HttpServlet {

    @Override//I changed the next line from protected to public
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
    	
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
    	
		String userId = session.getAttribute("user_id").toString();
		//this is to get the history of user
//		JSONArray array = new JSONArray();
//		
//		DBConnection conn = DBConnectionFactory.getConnection();
//		try {
//			Set<Item> items = conn.getFavoriteItems(userId);
//			for (Item item : items) {
//				JSONObject obj = item.toJSONObject();
//				obj.append("favorite", true);
//				array.put(obj);
//			}
//			
//			RpcHelper.writeJsonArray(response, array);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} finally {
//			conn.close();
//		}
		
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		
		GeoRecommendation recommendation = new GeoRecommendation();
		//List<Item> items = recommendation.recommendItems(userId, lat, lon);
		
		String chatHistory=(String)request.getParameter("chatArea");
		String newChat=(String)request.getParameter("userInput");
		String answer="Bot: "+answerQuestion((String)session.getAttribute("user_id"), Double.parseDouble(request.getParameter("lat")), Double.parseDouble(request.getParameter("lon")), newChat)+"\n";
//		System.out.println(chatHistory+answer);
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
    String answerQuestion(String userId, double lat, double lon, String line) {
    	GeoRecommendation recommendation = new GeoRecommendation();
    	if(line.contains("What are my favorite categories?")) {
    		
    		DBConnection conn = DBConnectionFactory.getConnection();
    		JSONArray array = new JSONArray();
    		try {
    			Set<Item> items = conn.getFavoriteItems(userId);
//    			for (Item item : items) {
//    				JSONObject obj = item.toJSONObject();
//    				obj.append("favorite", true);
//    				array.put(obj);
//    			}
    			if(items.isEmpty()) {
        			return "You did not like any events yet.";    			
        		}
    			String categories = "";
    			for(String category : items.iterator().next().getCategories()) {
    				categories += " "+category;
    			}
        		return categories;
    			
    		} finally {
    			conn.close();
    		}
    		
//    		try {
//    			List<String> itemIds = new ArrayList<>();
//    			for(int i = 0; i < array.length(); ++i) {
//   	  			 itemIds.add(array.getString(i));
//   	  		 	}
//    			Set<Item> items = conn.getFavoriteItems(userId);
//    			for (Item item : items) {
//    				JSONObject obj = item.toJSONObject();
//    				obj.append("favorite", true);
//    				array.put(obj);
//    			}
//    			
//    			//RpcHelper.writeJsonArray(response, array);
//    		} catch (JSONException e) {
//    			e.printStackTrace();
//    		} finally {
//    			conn.close();
//    		}
    		
    		//List<Item> items= AppointmentsRepository.getTodaysAppointments(role, userId);
    		
    	}
    	if(line.contains("What are my recommended events?")) {
    		
    		
    		List<Item> items = recommendation.recommendItems(userId, lat, lon);
    		if(items.isEmpty()) {
    			return "We can only make recommendations after you like some events.";    			
    		}
    		return items.get(0).getName();
    		
    	}
    	return "I'm sorry. I did not understnad your question.";
    }
    
    
}