package com.example.cameraD;

import java.util.HashMap;
import java.util.Map;

public class CreateNewAlbumInputs implements InputParams{

	 private Map<String,String> mInputMap;	 
	 
	 public CreateNewAlbumInputs(
			 String newAlbumName,String userId ,String latitude,String altitude,String androidId,
			 String password,String action,String email) {
		 
		    mInputMap = new HashMap<String,String>();
	        mInputMap.put("newAlbumName", newAlbumName);
	        mInputMap.put("userId", userId);
	        mInputMap.put("latitude", latitude);
	        mInputMap.put("altitude", altitude);
	        mInputMap.put("androidId", androidId);
	        mInputMap.put("password", password);
	        mInputMap.put("action", action);
	        mInputMap.put("email", email);
	}

	 public Map<String,String> getInputMap() {
	        return mInputMap;
	}
}
