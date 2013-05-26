package com.example.cameraD;

import java.util.HashMap;
import java.util.Map;

public class RegistreNewUserInput implements InputParams {
	
	private Map<String,String> mInputMap;
	
	public RegistreNewUserInput(String androidId,String emailAddress,String password ,String action) {
		  	mInputMap = new HashMap<String,String>();
	        mInputMap.put("androidId", androidId);
	        mInputMap.put("emailAddress", emailAddress);
	        mInputMap.put("password", password);
	        mInputMap.put("action", action);	
	}
	
	@Override
	public Map<String, String> getInputMap() {
		 return mInputMap;
	}

}
