package Action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import testPackage.ActionExecuteInterface;
import Dao.CameraDao;
import Utils.CameraUtils;
import Utils.MyThreadPool;

public class RegisterNewUser implements ActionExecuteInterface {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp,
			JSONObject inputValues, MyThreadPool myThreadPool)
			throws ServletException, IOException {
		
		String emailAddress,password,androidId;
		RegisterNewUserInfo rnui = null;
		
		try {
			
			androidId  	 =  inputValues.getString("androidId");
			emailAddress =  inputValues.getString("emailAddress");
			password     =  inputValues.getString("password");
			
			/* if email send failed what to do with user?*/
			sendEmail("arnon.rodman1@gmail.com", emailAddress);
			
			//create  new profile folder and property file for new user.
			boolean success  = createNewProfileFolder(emailAddress.trim(),"","");
			
			//user folder already exists.
			if(!success){
				rnui = new RegisterNewUserInfo("FAILED user already exists");
				PrintWriter out = resp.getWriter();				
				out.println(new JSONObject(rnui));	
				resp.flushBuffer();
				resp.getWriter().close();
				return ;
			}
				
			CameraDao.INSTANCE.insertNewUser(androidId,emailAddress,password);			
			
			
			
			if(success)
				rnui = new RegisterNewUserInfo("SUCCESS");
			else
				rnui = new RegisterNewUserInfo("FAILED");
			
			PrintWriter out = resp.getWriter();				
			out.println(new JSONObject(rnui));	
			resp.flushBuffer();
			resp.getWriter().close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
	
	/**
	 * Create new folder for new user.
	 * @param emailAddress
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private boolean createNewProfileFolder(String emailAddress,String latitude,String altitude) throws FileNotFoundException, IOException{
		String albumProfileFolder ="C:/profiles/"+emailAddress;		
		boolean success  = (new File(albumProfileFolder.toString())).mkdirs();
		
		if(success){
			Properties prop = new Properties();
			prop.setProperty("profileName", emailAddress);
			prop.setProperty("ProfilecreateTime", CameraUtils.getCurentDate_dd_MM_yy());
			prop.setProperty("latitude", latitude);
			prop.setProperty("altitude", altitude);	
			
			prop.store(new FileOutputStream(albumProfileFolder+"/profile.properties"), null);
		}
		return success ;		
	}
	
	private  Boolean sendEmail(String fromE,String toE){
		Boolean result = null;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("arnon.rodman1","52745274");
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromE.trim()));
			//message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("arnon_rodman@yahoo.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toE.trim()));
			message.setSubject("Testing Subjec yahooooooooooo go albooooooooooooooooooot");
			message.setText("Welcome albuuuuuuuuuuuuuuuu," + "\n\n ola!");
 
			Transport.send(message);
 
			System.out.println("Done");
			result = true;
		} catch (MessagingException e) {
			result = false;
			//throw new RuntimeException(e);
			//add log
		}
		return result;
	}


}

