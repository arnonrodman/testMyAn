package com.example.cameraD;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

import android.app.Activity;
import android.os.AsyncTask;

public class BusinessCalculator extends AsyncTask<Activity, Integer,StringBuffer> {

	private Activity callerActivity;
	private StringBuffer businessNames;
	
	@Override
	protected StringBuffer doInBackground(Activity... params) {
		callerActivity = params[0];
		
		String str="https://maps.googleapis.com/maps/api/place/search/json?location=32.094682,34.793569&radius=100&sensor=false&key=AIzaSyDGVPpWbJuWgzcIabHNP1mbFQfeZy3P8Z8";
		businessNames =  new StringBuffer();
		try {
			 URLConnection con = new URL(str).openConnection();
	         con.connect();
	         BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	         String line = null,element=null;
	         StringTokenizer sToken = null;
	         
	         while ((line = reader.readLine()) != null) {	            
	            if(line.contains("name")){
	            	 element = line.toString().trim().substring(line.toString().trim().indexOf(":"), line.toString().trim().indexOf(","));
					 element = element.replace(":", "\0");
					 element = element.replaceAll("\\s","");
	            	 businessNames.append(element).append(",");
	            }
	         }
	     } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return businessNames;
	}

	
	@Override
	protected void onPostExecute(StringBuffer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		((CreateNewAlbumActivity)callerActivity).setBusinessNames(businessNames.toString());
		((CreateNewAlbumActivity)callerActivity).conectListViewToBusinessAroundMe();
	}
	
	

}
