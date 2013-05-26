package com.example.cameraD;

import java.util.HashMap;
import java.util.Map;

public class ValidateUserInput {
	
 private Map<String,String> mInputMap;	 
	 
	 public ValidateUserInput(String userName,String password ,String androidId,String action){
		    mInputMap = new HashMap<String,String>();
	        mInputMap.put("userName", userName);
	        mInputMap.put("password", password);
	        mInputMap.put("androidId", androidId);
	        mInputMap.put("action", action);
	}
	public Map<String,String> getInputMap() {
	        return(mInputMap);
	}

}
