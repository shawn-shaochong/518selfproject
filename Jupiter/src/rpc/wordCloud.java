package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import wordcloud.WordCloudCreation;
import wordcloud.WordCount;

/**
 * Servlet implementation class wordCloud
 */
@WebServlet("/wordcloud")
public class wordCloud extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public wordCloud() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// allow access only if session exists
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}

		// optional
		//String userId = session.getAttribute("user_id").toString();
		
    	String userId = request.getParameter("user_id");
		DBConnection conn = DBConnectionFactory.getConnection();
		Set<Item> items = conn.getFavoriteItems(userId);  			
		
		String words = "";
		for (Item item:items) {
			words += " "+item.getCategories();
			words += " "+item.getName();
		}
    	
    	words=words.replace("[", " ");
    	words=words.replace("]", " ");
    	words=words.replace(",", " ");
    	System.out.println(words);
    	
    	HashMap<String, Integer> map = new HashMap<>();
    	for(String word: words.split(" ")) {
    		if(map.get(word)==null) {
    			map.put(word,1);
    		}
    		else {
    			map.put(word, map.get(word)+1);
    		}
    	}
    	ArrayList<WordCount> wordCountList = new ArrayList<WordCount>();
    	for(Entry<String, Integer> entry : map.entrySet()) {
    		WordCount wc = new WordCount(entry.getKey(),entry.getValue());
    		wordCountList.add(wc);
    	}
    	
    	String output =WordCloudCreation.getWordCloudHTML("wordcloud",wordCountList);
    	response.getWriter().print(output);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
