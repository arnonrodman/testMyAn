package com.example.cameraD;

import java.io.BufferedReader;
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
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.Activity.CameraDemoActivity;
import com.example.Activity.LoginActivity;
import com.example.Activity.UserProfileActivity;

public class ValidateNewUser extends AsyncTask<String, Integer,Boolean> {
	private String email,androidId;
	private  Activity sender;
	
	public ValidateNewUser(Activity sender){
		this.sender = sender;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		email  = params[0];
		String password  = params[1];
		androidId = params[2];
		return isNewUser(email, password,androidId);
	}
	
	public Boolean isNewUser(String email,String password,String androidId){
		String sResponse = null;
		Boolean result = null;
		HttpParams param = new BasicHttpParams();
		//param.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		 
		HttpClient client = new DefaultHttpClient();
	    HttpPost httpPost = new HttpPost(CameraUtils.serverIp());		
	    JSONObject jsonResult = null;
	    ValidateUserInput inputs = new ValidateUserInput(email,password,androidId,"validateNewUser");
	    JSONObject inputsJson = new JSONObject(inputs.getInputMap());		    
		 
	    try{
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
			        
			     if(sResponse!= null && !"".equals(sResponse))
			       	jsonResult = new JSONObject(sResponse);
			 }
			    
		     if(jsonResult!= null && "VALIDUSER".equals(jsonResult.getString("result"))){
		    	 result = true;
			 }else if((jsonResult!= null && "NOUSEREXIST".equals(jsonResult.getString("result")))
					 	||jsonResult == null){
				 result = false;
			 }  		     
		}catch (Exception e) { 
 	      e.printStackTrace();
 	      return null;
		}
		return result;
	}
	
	
	
	@Override
	protected void onPostExecute(Boolean result) {		
		super.onPostExecute(result);
		LoginActivity mainactivity = (LoginActivity)sender ;
	    mainactivity.retunnumfromAsyncTask = result;
	    
	    if(Boolean.TRUE.equals(result)){
			Intent startNewActivityOpen = new Intent(sender, UserProfileActivity.class);
			startNewActivityOpen.putExtra("email", email);
			startNewActivityOpen.putExtra("androidId", androidId);
			sender.startActivityForResult(startNewActivityOpen, 0);
		}else{			
			TextView error=(TextView)sender.findViewById(R.id.tv_error);
			error.setText("Invalid user");
		}
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


}
