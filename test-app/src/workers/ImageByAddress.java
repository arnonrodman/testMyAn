package workers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.json.JSONObject;

public class ImageByAddress implements Callable<String> {
	
	private StringBuilder  places_search_url =  new StringBuilder("http://maps.google.com/maps/api/place/search/json?location=");	
	private String API_KEY = "AIzaSyDGVPpWbJuWgzcIabHNP1mbFQfeZy3P8Z8";
	private Long latitude,altitude;	
	 
	// JSON:  http://maps.google.com/maps/api/place/search/json?location=40.717859,-73.9577937&radius=1600&client=clientId&sensor=true_or_false&signature=SIGNATURE
	public ImageByAddress(Long latitude,Long altitude){		
		this.latitude = latitude;
		this.altitude = altitude;
		places_search_url.append(latitude).append(",").append(altitude).append("&radius=100&client=clientId&sensor=true&key=").append(API_KEY);
	}
	
	@Override
	public String call() throws Exception {		
		HttpURLConnection conn = null;
		URL url = null;
		try {
			//url = new URL("https://maps.googleapis.com/maps/api/place/search/json?location=32.094682,34.793569&radius=100&sensor=false&key=AIzaSyAYM55QQsg0i5rRAglnbM8BosfX2d8iNqk");
			url = new URL("https://maps.googleapis.com/maps/api/place/search/json?location="+latitude+","+altitude+"radius=100&sensor=false&key="+API_KEY);
			conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while((line = reader.readLine()) != null) {
			 builder.append(line);
			}
			System.out.println(builder.toString());
			JSONObject json = new JSONObject(builder.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return null;
	}
}
