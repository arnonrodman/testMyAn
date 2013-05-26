package Utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import testPackage.ActionExecuteInterface;



@WebServlet("/controllerServlet")
public class ControllerServlet extends HttpServlet {
	private ActionFactory actionFactory;

	@Override
	public void init() throws ServletException {		
		super.init();
		actionFactory = new ActionFactory();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		process(req, resp);
	}
	
	
	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//create local thread pool
		MyThreadPool myPool = new ThreadPoolFactory().craeteMyThreadPool();
		
		String inputString =req.getParameter("InputParams");
		JSONObject inputValues = null;
		String actionId;
		try {
			inputValues = new JSONObject(inputString);
			actionId = inputValues.getString("action");
		} catch (JSONException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		
		ActionExecuteInterface  action = actionFactory.getAction(actionId);		
		action.execute(req, resp,inputValues,myPool);		
	}
	
	
}
