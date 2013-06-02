package com.example.Activity;

import java.io.FileOutputStream;
import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.cameraD.CameraUtils;
import com.example.cameraD.ImageUploader;
import com.example.cameraD.R;


public class CameraDemoActivity extends Activity {	  
	  private Preview preview;
	  private Button buttonClick;
	  private LocationManager locationManager;
	  private String 		  filePath,albumName,imageID,email,newAlbumName;	  
	  private Location  	  myLastLocation;
	  private String 		  androidId;	  
	  
	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	   
	    androidId = Secure.getString(getBaseContext().getContentResolver(),Secure.ANDROID_ID);
	    
	    email = getIntent().getExtras().getString("email");	    
	    setAlbumName(newAlbumName = getIntent().getExtras().getString("newAlbumName"));
	    myLastLocation = (Location)getIntent().getExtras().get("location");
	    
	    getLocalApps();
	    
	    preview = new Preview(this); 
	    ((FrameLayout) findViewById(R.id.preview)).addView(preview);
	    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	    
	    buttonClick = (Button) findViewById(R.id.buttonClick);
	    buttonClick.setOnClickListener(new OnClickListener() {	      
	      public void onClick(View v) {     	
	        preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
	       // ((FrameLayout) findViewById(R.id.preview)).removeView(preview); 
	       // ((FrameLayout) findViewById(R.id.preview)).addView(preview)	        
	      }
	    });	   
	  }

	  // Called when shutter is opened
	  ShutterCallback shutterCallback = new ShutterCallback() { 
	    public void onShutter() {}
	  };

	  // Handles data for raw picture
	  PictureCallback rawCallback = new PictureCallback() {
	    public void onPictureTaken(byte[] data, Camera camera) {}
	  };

	  // Handles data for jpeg picture
	  PictureCallback jpegCallback = new PictureCallback() { 
	    public void onPictureTaken(byte[] data, Camera camera) {
	      FileOutputStream outStream = null;
	      boolean tmpUser = false;
	      try {
	    	  String root = Environment.getExternalStorageDirectory().toString();
	    	   
	    	if(getAlbumName() == null){
	    		  tmpUser = true;
	    		  setFilePath(String.format(root+"/"+androidId+"/%d.jpg", System.currentTimeMillis()));	
	    	}else{
	    		  setFilePath(String.format(root+"/"+getAlbumName()+"/%d.jpg", System.currentTimeMillis()));	 
	    	}    	
	        
	    	// Write to SD Card    	
	    	outStream = new FileOutputStream(getFilePath());	        
	    	outStream.write(data);
	        outStream.close();
	        
	        StringTokenizer stk = new StringTokenizer(filePath,"/");
	        String token = null;
	        
	        while(stk.hasMoreTokens()){
	        	token = stk.nextToken();
	        	if(token.contains(".jpg")){
	        		setImageID(token);
	        		break;
	        	}
	        }
	        
	        if(myLastLocation == null)
	        	myLastLocation = CameraUtils.getMyLocation(locationManager);
	        
	        new ImageUploader().execute(
	        		filePath,
	        		getImageID(),
	        		newAlbumName,
	        		androidId,
	        		String.valueOf(tmpUser),
	        		String.valueOf(myLastLocation.getAltitude()),String.valueOf(myLastLocation.getLongitude()));
	        
	        
	      } catch (Exception e) { 
	    	  e.printStackTrace();
	    	  System.gc();
	    	  finish();	    	 
	      } finally {
	      
	      }	     
	    }
	  };

	  
	private List<ApplicationInfo> getLocalApps(){
		  List<ApplicationInfo> localApplications = this.getPackageManager().getInstalledApplications(0); 
		  
		  for(ApplicationInfo app:localApplications){
			  
		  }
		  return localApplications;
	}
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}
	
}