package workers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AddressWorker implements Runnable {
	private String latitude,longitude ;
	
	public AddressWorker(String latitude,String longitude,String imageToBeSaved,String localUserAlbumFolde){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	@Override
	public void run() {
		//String str="http://maps.googleapis.com/maps/api/geocode/json?latlng=40.717859,-73.9577937&sensor=true";
		String str="http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&sensor=true";		
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
