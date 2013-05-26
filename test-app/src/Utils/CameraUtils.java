package Utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraUtils {
	
	public static String getCurentDate_dd_MM_yy(){
		Format formatter = new SimpleDateFormat("dd-MM-yy");
		return  formatter.format(new Date());	
	}

}
