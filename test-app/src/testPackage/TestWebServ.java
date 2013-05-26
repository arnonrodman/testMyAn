package testPackage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class TestWebServ {
	
public static void main(String[] args) {
		testPlace();
		testGoogle();
}
	
public static void testPlace(){
		HttpURLConnection conn = null;
		URL url = null;
		try {
			//url = new URL( "http://maps.google.com/maps/api/place/search/json?location=40.717859,-73.9577937&radius=1600&client=clientId&sensor=true&key=AIzaSyDGVPpWbJuWgzcIabHNP1mbFQfeZy3P8Z8");
			url = new URL("https://maps.googleapis.com/maps/api/place/search/json?location=32.094682,34.793569&radius=100&sensor=false&key=AIzaSyDGVPpWbJuWgzcIabHNP1mbFQfeZy3P8Z8");
			conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String element;
			while((line = reader.readLine()) != null) {
			 builder.append(line);
			 
			 if(line.contains("name")){
				 element = line.toString().trim().substring(line.toString().trim().indexOf(":"), line.toString().trim().indexOf(","));
				 element = element.replace(":", "\0");
				 element = element.replaceAll("\\s","");
				 System.out.println(element);
			 }
			}
			System.out.println(builder.toString());
			//JSONObject json = new JSONObject(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
}
	
public static void testGoogle(){
		String str="http://maps.googleapis.com/maps/api/geocode/json?latlng=40.717859,-73.9577937&sensor=true";
		URL url;
		try {
			 URLConnection con = new URL(str).openConnection();
	         con.connect();
	         BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	         String line = null;
	         while ((line = reader.readLine()) != null) {
	            System.out.println(line);
	         }
	         
			url = new URL(str);
		
			InputStream is = url.openStream();
			int ptr = 0;
			StringBuilder builder = new StringBuilder();
			while ((ptr = is.read()) != -1) {
			    builder.append((char) ptr);
			}
			String xml = builder.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}