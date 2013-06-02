package com.example.cameraD;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.Activity.RegisterActivity;
import com.example.Activity.UserProfileActivity;

public class RegisterNewUser extends AsyncTask<String, Integer, Boolean> {

	private String emailAddress,androidId;
	private  Activity sender;
	
	
	public RegisterNewUser(Activity sender) {
		super();
		this.sender = sender;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String password,sResponse;
		
		androidId 	 = params[0];
		emailAddress = params[1];
		password 	 = params[2];
		
		try {
			 HttpParams param = new BasicHttpParams();
			 //param.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			 
			 HttpClient client = new DefaultHttpClient();
		     HttpPost httpPost = new HttpPost(CameraUtils.serverIp());
		     InputParams inputs = new RegistreNewUserInput(androidId, emailAddress, password,"registreNewUser");		     
		     JSONObject inputsJson = new JSONObject(inputs.getInputMap());	
		     
		     UrlEncodedFormEntity entity = new UrlEncodedFormEntity(CameraUtils.acivate("InputParams",inputsJson.toString()), "UTF-8");
			 httpPost.setEntity(entity);
	
			 HttpResponse  res = client.execute(httpPost);
			 int statusCode = res.getStatusLine().getStatusCode();
		     
			 if(statusCode!= 200)
				 return false;
			 
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
		    	 return true;
			 }else if("FAILED".equals(jsonResult.getString("result"))){
				 return false;
			 }
		} catch (Exception e) {
			e.printStackTrace();			
		}     
		
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		RegisterActivity mainactivity = (RegisterActivity)sender ;
	   // mainactivity.retunnumfromAsyncTask = result;
	    
	    if(result){
			Intent startNewActivityOpen = new Intent(sender, UserProfileActivity.class);
			startNewActivityOpen.putExtra("email", emailAddress);
			startNewActivityOpen.putExtra("androidId", androidId);
			sender.startActivityForResult(startNewActivityOpen, 0);
		}else{			
			TextView error=(TextView)sender.findViewById(R.id.tv_error);
			error.setText("Invalid user");
		}
	}
	

}
