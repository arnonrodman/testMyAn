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
		String altitude					= null;
		String email				    = null;
		
		try {			
			myImage   =  inputValues.getString("myImage");
			filePath  =  inputValues.getString("filePath");
			imageId   =  inputValues.getString("folder");
			albumName =  inputValues.getString("albumName");
			androidId =  inputValues.getString("androidId");
			longitude =  inputValues.getString("longitude");
			altitude  =  inputValues.getString("altitude");
			email     =  inputValues.getString("email");
												
			//load image from String to byte array.
			byte[] roundTrip = Base64.decodeBase64(myImage);	
			fileContentImage = new ByteArrayInputStream(roundTrip);
			
			if(fileContentImage != null && imageId != null && androidId!= null){
				 //create and save new email/user folder.
				localUserAlbumFolder = "C:/profiles/"+email+"/"+albumName;
								
				//update album property file
								
				//save input image into previous folder.	
				imageToBeSaved = localUserAlbumFolder+"\\"+"_longitude"+longitude+"_latitude_"+altitude+"_imageId"+imageId;
				out = new FileOutputStream(imageToBeSaved);
				copy(fileContentImage,out);
				
				out.flush();
				out.close();				        
			}
						
			//activate address worker to find picture address.
			//myThreadPool.executeGoogleAddress(altitude,longitude,imageToBeSaved,localUserAlbumFolder);
			
			//activate get image from address (altitude, latitude)
			//String image = myThreadPool.executeGoogleImageByAddress(longitude, altitude);
			
			//activate google search by name.
			//image = myThreadPool.executeImageByName("image name");
			
			CreateNewPhotoResult cna = new CreateNewPhotoResult("SUCCESS",localUserAlbumFolder,"image");
			PrintWriter pw = resp.getWriter();				
			pw.println(new JSONObject(cna));
			
		} catch (Exception e) {			
			e.printStackTrace();
			throw new ServletException();
		}
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
