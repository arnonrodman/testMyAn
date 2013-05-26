package com.example.cameraD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class CameraUtils {
	
	public static String getCurentDate_dd_MM_yy(){
		Format formatter = new SimpleDateFormat("dd-MM-yy");
		return  formatter.format(new Date());	
	}
	
	public static Location getMyBestLocation(Context mContext){
		Location myLocation = null;
		LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		
		// getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String  locationProvider = locationManager.getBestProvider(criteria, true);	
        LocationListener locationLis = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				
			}
		};
        
		locationManager.requestLocationUpdates(locationProvider, 0, 0, locationLis);
		
        if (isNetworkEnabled) {
           if (locationManager != null) {
            	myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);               
            }
        }else if(isGPSEnabled){
        	myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return myLocation;
	}
	
	public static Location getMyLocation(LocationManager locationManager){		
		LocationListener locationLis = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				
			}
		};
		
		Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        String  locationProvider = locationManager.getBestProvider(criteria, true);	
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationLis);
    	Location myLastLocation =   locationManager.getLastKnownLocation(locationProvider);
    	locationManager.removeUpdates(locationLis);
    	return myLastLocation;
	}
	
	public static  List<NameValuePair> acivate( String ... paramNamesAndValues) throws IOException, ClientProtocolException {		    
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    for(int i=0; i<paramNamesAndValues.length-1; i=i+2) {
	      String paramName = paramNamesAndValues[i];
	      String paramValue = paramNamesAndValues[i+1];  // NOT URL-Encoded
	      params.add(new BasicNameValuePair(paramName, paramValue));
	    }
	    return params;		   
	}
	
	public static String serverIp(){
		return "http://172.16.8.48:8080/test-app/controllerServlet";
		//return "http://10.0.0.8/test-app/controllerServlet";
	}
	
	public static Bitmap getImageByName(String name)throws Exception{
        URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+name);
        URLConnection connection = url.openConnection();

        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }

        JSONObject json = new JSONObject(builder.toString());
        String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");
        url = new URL(imageUrl);
       return  BitmapFactory.decodeStream(url.openConnection().getInputStream());	          	                 
}
	

}
