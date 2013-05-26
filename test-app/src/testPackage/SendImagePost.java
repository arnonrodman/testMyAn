package testPackage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

@WebServlet("/sendImagePost")
public class SendImagePost extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		super.doPost(req, resp);
		try {
		    List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
		    InputStream  fileContentImage = null;
		    String       fileContentType  = null;
		    String 		 folder = null;
		    String androidId = null;		
		    OutputStream out = null;
		    
		    for (FileItem item : items) {
		        if (item.getFieldName().equals("image1")) {		        	
		            fileContentType = item.getContentType();
		            fileContentImage = item.getInputStream();	        
		        }else if(item.getFieldName().equals("folder")){
		        	folder = item.getContentType();
		        }else if(item.getFieldName().equals("androidId")){
		        	androidId = item.getContentType();
		        }
		    }	
		    if(fileContentImage != null && folder != null && androidId!= null){
		    	out = new FileOutputStream("C:\\"+folder+"_"+androidId+"_myfile.jpg");
		        copy(fileContentImage,out);
		        out.flush();
		        out.close();
		    }
		    
		} catch (FileUploadException e) { 
		    throw new ServletException("Cannot parse multipart request.", e);
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
