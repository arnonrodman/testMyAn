package Action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import org.json.JSONObject;

import testPackage.ActionExecuteInterface;
import testPackage.ValidateUserResult;
import Dao.CameraDao;
import Utils.MyThreadPool;

import Dao.CameraDao;

public class ValidateUser implements ActionExecuteInterface {
	String eMail,password,androidId;
	
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp,
			JSONObject inputValues,MyThreadPool myThreadPool) throws ServletException, IOException {
		ValidateUserResult cna = null;
		try {							
				eMail =   inputValues.getString("userName");
				password =   inputValues.getString("password");			
				androidId =  inputValues.getString("androidId");
						
				if(eMail!= null)
					eMail = eMail.trim();
				

				if(password!= null)
					password = password.trim();
				
	    		//Create a cluster object from your existing Cassandra cluster
	            Cluster cluster = HFactory.getOrCreateCluster("Test Cluster", "localhost:9160");
	            
	            //Create a Keyspace object from the existing Keyspace we created using CLI
	            Keyspace keyspace = HFactory.createKeyspace(CameraDao.keySpace, cluster);
	           
	    		SliceQuery<String, String, String> sliceQuery = HFactory.createSliceQuery(keyspace, CameraDao.stringSerializer, CameraDao.stringSerializer, CameraDao.stringSerializer);
	            sliceQuery.setColumnFamily("users").setKey(eMail.trim());
	            sliceQuery.setRange("", "", false, Integer.MAX_VALUE);
	            QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute(); 
	          	ColumnSlice<String, String> res = result.get();
	    		
	    		if(res.getColumns().isEmpty()){
	    			cna = new ValidateUserResult("NOUSEREXIST");
	    		}else{
	    			String dbPass =  res.getColumnByName("password").getValue();
	    			if(dbPass!= null && dbPass.trim().equals(password)){
	    				cna = new ValidateUserResult("VALIDUSER");
	    			}	    			
	    		}
	    		
				PrintWriter out = resp.getWriter();				
				out.println(new JSONObject(cna));
				resp.flushBuffer();
				resp.getWriter().close();
				
	    	} catch (Exception ex) {
	    		System.out.println("Error encountered while retrieving data!!");
	    		ex.printStackTrace() ;
	    	}		
		

	}

}
