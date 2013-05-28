package com.example.cameraD;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;

public class NewAlbumAsyncTask extends AsyncTask<String, Integer, String> {
	private String sResponse,resultImageURL;
	private Activity sender;
	
	@Override
	protected String doInBackground(String... params) {
		String newAlbumName = params[0];
		String testUserId 	= params[1];
		String latitude 	= params[2];
		String altitude 	= params[3];
		String androidId 	= ( params[4]!= null ?  params[4] :String.valueOf((Math.random()*1+10)));
		String password 	=( params[4]!= null ?  params[4] :String.valueOf((Math.random()*1+10)));
		String email        = params[5];		
		
		if(newAlbumName!= null){
			try{
				 HttpParams param = new BasicHttpParams();
				 //param.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
				 
				 HttpClient client = new DefaultHttpClient();
			     HttpPost httpPost = new HttpPost(CameraUtils.serverIp());
			     InputParams inputs = new CreateNewAlbumInputs(newAlbumName, testUserId, latitude, altitude, androidId, password,"createNewAlbum",email);		     
			     JSONObject inputsJson = new JSONObject(inputs.getInputMap());		    
				 
			     UrlEncodedFormEntity entity = new UrlEncodedFormEntity(acivate("InputParams",inputsJson.toString()), "UTF-8");
				 httpPost.setEntity(entity);
	
				 HttpResponse  res = client.execute(httpPost);
				 int statusCode = res.getStatusLine().getStatusCode();
			     
				 if(statusCode == 200){
					 InputStream is =res.getEntity().getContent();
				     BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);		    
				     StringBuilder sb = new StringBuilder();
				     String line = null;
				     while ((line = reader.readLine()) != null) {
				            sb.append(line + "\n");
				     }
				     is.close();
				     sResponse = sb.toString();
				        
				     JSONObject jsonResult = new JSONObject(sResponse);
				     if("SUCCESS".equals(jsonResult.getString("result"))){
				    	 sResponse = jsonResult.getString("newAlbumName");
				    	 resultImageURL = jsonResult.getString("resultImageURL");
				    	
				    	 //create local SD folder
					     File localSDAlbumFolder = new File(Environment.getExternalStorageDirectory() + "/"+newAlbumName);
					     if(!localSDAlbumFolder.exists())
					      {
					          if(localSDAlbumFolder.mkdir()) 
					            {
					             //folder is created;
					        	 // CreateNewAlbumActivity.re
					            }
					      }
					 }else if("FAILED".equals(jsonResult.getString("result"))){
						 System.out.println();
					 }
				     
				     
				 }else{
					 //failed connect to remote server
				 }	  		     
			}catch (Exception e) { 
	 	      e.printStackTrace();       	 
			}
		}
		
		return sResponse;
	}
		
	
	private List<NameValuePair> acivate( String ... paramNamesAndValues) throws IOException, ClientProtocolException {		    
		    List<NameValuePair> params = new ArrayList<NameValuePair>();
		    for(int i=0; i<paramNamesAndValues.length-1; i=i+2) {
		      String paramName = paramNamesAndValues[i];
		      String paramValue = paramNamesAndValues[i+1];  // NOT URL-Encoded
		      params.add(new BasicNameValuePair(paramName, paramValue));
		    }
		    return params;		   
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
	}
	
	

}
