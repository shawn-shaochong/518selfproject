package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Item;

public class RpcHelper {
	
	// Parses a JSONObject from http request.
	//it will be in 'favorite':['item_id1','item_id2']   format
	public static JSONObject readJSONObject(HttpServletRequest request) {
	  	   StringBuilder sBuilder = new StringBuilder();
	  	   try (BufferedReader reader = request.getReader()) {
	  		 String line = null;
	  		 while((line = reader.readLine()) != null) {
	  			 sBuilder.append(line);
	  		 }
	  		 return new JSONObject(sBuilder.toString());
	  		
	  	   } catch (Exception e) {
	  		 e.printStackTrace();
	  	   }
	  	
	  	  return new JSONObject();
	}

	
	
	
	// Writes a JSONArray to http response.
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException {
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();	
		out.print(array);
		out.close();
	}


	// Writes a JSONObject to http response.
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();	
		out.print(obj);
		out.close();
	}


	
	public static JSONArray getJSONArray(List<Item> items) {
		JSONArray result = new JSONArray();
		try {
			for (Item item : items) {
				result.put(item.toJSONObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<String> getValuesForGivenKey(String jsonArrayStr, String key) throws JSONException {
	    JSONArray jsonArray = new JSONArray(jsonArrayStr);
	    return IntStream.range(0, jsonArray.length())
	      .mapToObj(index -> {
			try {
				return ((JSONObject)jsonArray.get(index)).optString(key);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return key;
		})
	      .collect(Collectors.toList());
	}
	
}
