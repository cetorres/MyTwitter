package br.com.cacira.meutwitter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TwitterApi {
	
	public static final String REST_URL = "http://search.twitter.com/search.json?rpp=100&q=";
	
	public static List<Tweet> getTweetsFromSearch(String searchTerms) {
	
		List<Tweet> tweetList = null;
		
		try {
			// Faz chamada HTTP
			HttpClient client = new  DefaultHttpClient();
			HttpGet get = new HttpGet(REST_URL + URLEncoder.encode(searchTerms, "UTF-8"));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();		
			String responseBody = client.execute(get, responseHandler);			
			
			// Faz a leitura do JSON recebido do Twitter
			JSONObject jsonObj = new JSONObject(responseBody);
			JSONArray jsonArray = jsonObj.getJSONArray("results");
			tweetList = new ArrayList<Tweet>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj2 = jsonArray.getJSONObject(i);
				String text = jsonObj2.getString("text");			
				String dateTime = jsonObj2.getString("created_at");
				String userImage = jsonObj2.getString("profile_image_url");
				String userScreenName = jsonObj2.getString("from_user");
				String userName = jsonObj2.getString("from_user_name");
				String userLocation = jsonObj2.getString("iso_language_code");
				
				Tweet tweet = new Tweet(userName, userScreenName, userImage, userLocation, text, dateTime);
				tweetList.add(tweet);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
		return tweetList;
	}
}
