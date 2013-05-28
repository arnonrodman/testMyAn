package com.example.cameraD;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

public class ImageUploader extends AsyncTask<String, Integer, String> {
	private Bitmap bitmap;
	private String sResponse;
		
	@Override
	protected String doInBackground(String... arg0) {
		 try {
			 String filePath    = arg0[0];
			 
			 //load and decode image.
			 decodeFile(filePath);
			 
			 String imageId		= arg0[1];
			 String albumName	= arg0[2];
			 String androidId   = arg0[3];
			 boolean tmpUser    = Boolean.valueOf(arg0[4]);			
			 String altitude    = arg0[5];
			 String longitude   = arg0[6];

			 ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 bitmap.compress(CompressFormat.JPEG, 100, bos);
			 byte[] data = bos.toByteArray();

			 HttpParams param = new BasicHttpParams();
			 //param.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			 String image64 = Base64.encodeToString(data, Base64.DEFAULT);

			 HttpClient client = new DefaultHttpClient();
		     HttpPost httpPost = new HttpPost(CameraUtils.serverIp());
		     InputParams inputs = new CreatePhotoInput(image64,filePath, imageId, albumName, androidId, String.valueOf(tmpUser),"createNewPhoto",altitude,longitude);     
		     JSONObject inputsJson = new JSONObject(inputs.getInputMap());		    
			 
		     UrlEncodedFormEntity entity = new UrlEncodedFormEntity(acivate("InputParams",inputsJson.toString()), "UTF-8");
			 httpPost.setEntity(entity);
			 
			 HttpResponse  res = client.execute(httpPost);
			 int statusCode = res.getStatusLine().getStatusCode();
		     
			 if(statusCode!= 200)
				 return null;
			 
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
		    	 sResponse = jsonResult.getString("localUserAlbumFolde");
			 }else if("FAILED".equals(jsonResult.getString("result"))){
				 sResponse = null;
			 }  		     
		}catch (Exception e) { 
 	      e.printStackTrace();       	 
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

	public void decodeFile(String filePath) {		 
		   // Decode image size
		   BitmapFactory.Options o = new BitmapFactory.Options();
		   o.inJustDecodeBounds = true;
		   BitmapFactory.decodeFile(filePath, o);
		
		   // The new size we want to scale to
		   final int REQUIRED_SIZE = 1024;
		
		   // Find the correct scale value. It should be the power of 2.
		   int width_tmp = o.outWidth, height_tmp = o.outHeight;
		   int scale = 1;
		   while (true) {
			   if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)		 
		                 break;
		 
		        width_tmp /= 2;		 
		        height_tmp /= 2;		
		        scale *= 2;		 
		   }
		 
		   // Decode with inSampleSize
		   BitmapFactory.Options o2 = new BitmapFactory.Options();
		   o2.inSampleSize = scale;
		   bitmap = BitmapFactory.decodeFile(filePath, o2);
		 }


}
