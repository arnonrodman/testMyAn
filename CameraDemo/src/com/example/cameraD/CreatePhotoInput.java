package com.example.cameraD;

import java.util.HashMap;
import java.util.Map;

public class CreatePhotoInput implements InputParams {

	private Map<String,String> mInputMap;
	
	
	public CreatePhotoInput( String myImage,String filePath,String folder, 
			String albumName,String androidId,String tmpUser,String action,String altitude,String longitude,String email) {		 	
			mInputMap = new HashMap<String, String>();
			mInputMap.put("myImage", myImage);
			mInputMap.put("filePath", filePath);
	        mInputMap.put("folder", folder);
	        mInputMap.put("albumName", albumName);	       
	        mInputMap.put("androidId", androidId);
	        mInputMap.put("tmpUser", tmpUser);
	        mInputMap.put("action", action);
	        mInputMap.put("altitude", altitude);
	        mInputMap.put("longitude", longitude);
	        mInputMap.put("email", email);
	}


	public Map<String,String> getInputMap() {
        return(mInputMap);
	}

}
