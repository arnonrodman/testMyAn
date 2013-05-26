package Utils;

import java.util.HashMap;
import java.util.Map;

import Action.CreateNewAlbum;
import Action.CreateNewPhoto;
import Action.RegisterNewUser;
import Action.ValidateUser;

import testPackage.ActionEnum;
import testPackage.ActionExecuteInterface;

public class ActionFactory {
	private Map<String,ActionExecuteInterface> actions= new HashMap<String, ActionExecuteInterface>();
		
	public ActionExecuteInterface getAction(String actionCode){
		ActionExecuteInterface action = actions.get(actionCode);
		if(action == null){
			if(ActionEnum.validateNewUser.toString().equals(actionCode)){
				action = new ValidateUser();
			}
			if(ActionEnum.createNewAlbum.toString().equals(actionCode)){ 
				action = new CreateNewAlbum();				
			}
			if(ActionEnum.createNewPhoto.toString().equals(actionCode)){ 
				action = new CreateNewPhoto();				
			}
			if(ActionEnum.registreNewUser.toString().equals(actionCode)){ 
				action = new RegisterNewUser();				
			}
			actions.put(actionCode, action);
		}
		return action;
		
	}
	
	

}
