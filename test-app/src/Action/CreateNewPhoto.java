package Action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import testPackage.ActionExecuteInterface;
import testPackage.CreateNewPhotoResult;
import Dao.CameraDao;
import Utils.CameraUtils;
import Utils.MyThreadPool;



public class CreateNewPhoto implements ActionExecuteInterface {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp,
			JSONObject inputValues,MyThreadPool myThreadPool) throws ServletException, IOException {
		
		String myImage,filePath,imageId,albumName,androidId;
		OutputStream out 				= null;
		InputStream  fileContentImage   = null;
		String localUserAlbumFolder		= null;
		String imageToBeSaved 			= null;
		String longitude				= null;
		String latitude					= null;
		
		try {			
			myImage   =  inputValues.getString("myImage");
			filePath  =  inputValues.getString("filePath");
			imageId   =  inputValues.getString("folder");
			albumName =  inputValues.getString("albumName");
			androidId =  inputValues.getString("androidId");
			longitude =  inputValues.getString("longitude");
			latitude  =  inputValues.getString("latitude");
			
			//validate new user at DB.
			boolean isNewUser = false;
			//load image from String to byte array.
			localUserAlbumFolder = validteExitsUserInDBAlbume(androidId);
			byte[] roundTrip = Base64.decodeBase64(myImage);	
			fileContentImage = new ByteArrayInputStream(roundTrip);
			
			if(fileContentImage != null && imageId != null && androidId!= null){
				 //create and save new android/user folder.
				if(localUserAlbumFolder == null)	{	
					localUserAlbumFolder = "C:\\deviceId"+"_"+androidId+"_"+CameraUtils.getCurentDate_dd_MM_yy();
					isNewUser = true;
				}
				
				File newFile =  new File(localUserAlbumFolder.toString());
				newFile.mkdir();
				newFile.setWritable(true);
				
				//save input image into previous folder.	
				imageToBeSaved = localUserAlbumFolder+"\\"+"_longitude"+longitude+"_latitude_"+latitude+"_"+imageId;
				out = new FileOutputStream(imageToBeSaved);
				copy(fileContentImage,out);
				
				out.flush();
				out.close();				        
			}
			//if new user insert to DB.
			if(isNewUser)
				CameraDao.INSTANCE.insertNewAlbumDB(null,null,null,localUserAlbumFolder,null,null,androidId);
			
			//activate address worker to find picture address.
			myThreadPool.executeGoogleAddress(latitude,longitude,imageToBeSaved,localUserAlbumFolder);
			
			//activate get image from address (altitude, latitude)
			String image = myThreadPool.executeGoogleImageByAddress(longitude, latitude);
			
			//activate google search by name.
			image = myThreadPool.executeImageByName("image name");
			
			CreateNewPhotoResult cna = new CreateNewPhotoResult("SUCCESS",localUserAlbumFolder,image);
			PrintWriter pw = resp.getWriter();				
			pw.println(new JSONObject(cna));
			
			
			
		} catch (Exception e) {			
			e.printStackTrace();
			throw new ServletException();
		}
	}
		
	private String validteExitsUserInDBAlbume(String androidID){
		return CameraDao.INSTANCE.validteExitsUserInDB(androidID);
		
	}
	
	public static long copy(InputStream input, OutputStream output) throws IOException {
	    byte[] buffer = new byte[4096];

	    long count = 0L;
	    int n = 0;

	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	        count += n;
	    }
	    return count;
	}	
}
