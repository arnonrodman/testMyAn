package Action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import testPackage.ActionExecuteInterface;
import testPackage.CreateNewAlbumInfo;
import Dao.CameraDao;
import Utils.CameraUtils;
import Utils.MyThreadPool;

public class CreateNewAlbum implements ActionExecuteInterface {	
String newAlbumName,userId,albumFolderLocation,currentDate,latitude,altitude,androidId,password,email,resultImageURL;	
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp,JSONObject inputValues,MyThreadPool myThreadPool)
				throws ServletException, IOException {
		try {
			email    =  inputValues.getString("email");
			newAlbumName = inputValues.getString("newAlbumName");
			userId   =  inputValues.getString("userId");
			latitude =  inputValues.getString("latitude");
			altitude =  inputValues.getString("altitude");
			androidId = inputValues.getString("androidId");
			password =  inputValues.getString("password");
			
		} catch (JSONException e) {			
			e.printStackTrace();
			throw new ServletException();
		}
			
		currentDate = CameraUtils.getCurentDate_dd_MM_yy();	
		boolean sucess= false;
		
		if (userId != null && newAlbumName != null && latitude!= null && altitude!= null) {			
			//create new folder "newAlbumName".
			albumFolderLocation ="C:/profiles/"+email+"/"+newAlbumName;
			
			//validate  folder/new album already exists
			//validate  folder/new album already exists
			File newAlbumeFolder = new File(albumFolderLocation.toString());
			if(!newAlbumeFolder.exists()){
				//create new folder/new album folder
				sucess = newAlbumeFolder.mkdir();
				if(sucess){
					insertNewAlbumDB(userId,password,newAlbumName,albumFolderLocation,latitude,altitude,androidId,email);
					
					createAlbumProperties(newAlbumName, email, albumFolderLocation);
					
					resultImageURL = generateImagesResponseURl(newAlbumName);	
					
					CreateNewAlbumInfo cna = new CreateNewAlbumInfo("SUCCESS",newAlbumName,resultImageURL, albumFolderLocation);
					PrintWriter out = resp.getWriter();				
					out.println(new JSONObject(cna));				
				}else{	
					PrintWriter out = resp.getWriter();										
					out.println(new JSONObject(new CreateNewAlbumInfo("FAILED",newAlbumName,resultImageURL, albumFolderLocation)));
				}
			}else{
				PrintWriter out = resp.getWriter();					
				out.println(new JSONObject(new CreateNewAlbumInfo("FAILED albume allready exsits", newAlbumName,resultImageURL,albumFolderLocation)));
			}
		}
		resp.flushBuffer();
		resp.getWriter().close();
	}
		
	public String generateImagesResponseURl(String newAlbumName){
		//retrieve "newAlbumName" images folder for album background.
		File newAlbumNameImages = new File("c://images/"+newAlbumName);
		int number = 0;
		String responeBackgroundURL = null,randomImage = null;
		
		//id exists newAlbumName folder
		if(newAlbumNameImages.exists()){			
			number = (int)(Math.random() * 5) + 1;
			number=1;
			randomImage =newAlbumName+number+".jpg";
			newAlbumNameImages = new File("c://images/"+newAlbumName+"/"+randomImage);
			if(newAlbumNameImages.exists()){
				//http://localhost:8080/images/wedding/wedding1.jpg
				responeBackgroundURL = "http://localhost/images/"+newAlbumName+"/"+randomImage;
			}
		}
		
		return responeBackgroundURL;
	}
	
	private void createAlbumProperties(String albumName,String emailAddress,String albumFolder) throws FileNotFoundException, IOException{
		Properties prop = new Properties();
		prop.setProperty("profileName", emailAddress);
		prop.setProperty("ProfilecreateTime", CameraUtils.getCurentDate_dd_MM_yy());
		prop.setProperty("latitude", latitude);
		prop.setProperty("altitude", altitude);	
		prop.setProperty("albumName", albumName);	
		
		prop.store(new FileOutputStream(albumFolder+"/"+albumName+".properties"), null);
	}
	
	public String generateBackURL(String name){
		int number = (int)(Math.random()*10)+1;
		String backround = name+number;
		File nameFile = new File("c://imegas/"+name+"/"+backround);
		String returnUrl = null;
		if(nameFile.isFile()){
			//http://172.16.8.48:8080/images/wedding/wedding2.jpg
			returnUrl = "http://172.16.8.48:8080/images/"+name+"/"+backround;
		}
		return returnUrl;
		
	}
	
	public void insertNewAlbumDB(String... params){
		CameraDao.INSTANCE.insertNewAlbumDB(params);
	}	
}
