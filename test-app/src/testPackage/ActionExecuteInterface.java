package testPackage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Utils.MyThreadPool;


public interface ActionExecuteInterface {
	
	public void execute(HttpServletRequest req, HttpServletResponse resp,JSONObject inputValues,MyThreadPool myThreadPool)throws ServletException, IOException;

}
