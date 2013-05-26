package com.example.Activity;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cameraD.R;

public class AlbumSinglePageActivity extends Activity {

	private LinearLayout mLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_album_single_page);

		 // Create a LinearLayout in which to add the ImageView
		  mLinearLayout = new LinearLayout(this);

		  // Instantiate an ImageView and define its properties
		  ImageView i = new ImageView(this);
		  i.setImageResource(R.id.backGround);
		  i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
		  i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		  
		  //for test load background from URL
		  Bitmap backBMap,bm1,bm2,bm3,bmOverlay,rotatBm1,rotatBm2,rotatBm3;
		  
		  try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		    InputStream inThi = new java.net.URL("http://4.bp.blogspot.com/-7mIQBb3F2z0/ULKPDFzo28I/AAAAAAAAAuM/yRTk3oIevaQ/s1600/Thailand.jpg").openStream();
            backBMap =  BitmapFactory.decodeStream(inThi);
		    // i.setImageBitmap(backBMap);
            		    
		    bm1 = BitmapFactory.decodeStream(new java.net.URL("http://3.bp.blogspot.com/-o2Z5bMXexEI/TdVWeHs_66I/AAAAAAAAAZg/rWZ56XWH6_0/s1600/cute-baby.jpg").openStream());
		    bm2 = BitmapFactory.decodeStream(new java.net.URL("http://expertbeacon.com/sites/default/files/How%20to%20survive%20Thailands%20full%20moon%20party.jpg").openStream());
		    bm3 = BitmapFactory.decodeStream(new java.net.URL("http://gapyear.s3.amazonaws.com/images/made/images/content/12.10.10-mjs-volunteering-with-elephants-in-thailand-1_582_387.JPG").openStream());
		    		
		    
            //resize bm1,2,3
		    bm1 = Bitmap.createScaledBitmap(bm1, bm1.getWidth()/4, bm1.getWidth()/4, false);
		    bm2 = Bitmap.createScaledBitmap(bm2, bm2.getWidth()/4, bm2.getWidth()/4, false);
		    bm3 = Bitmap.createScaledBitmap(bm3, bm3.getWidth()/4, bm3.getWidth()/4, false);
		    
            //Set/copy original background BitMap.
		    bmOverlay = Bitmap.createBitmap(backBMap.getWidth(), backBMap.getHeight(),  backBMap.getConfig());
            Canvas canvas = new Canvas(bmOverlay);    
            canvas.drawBitmap(backBMap, 0, 0, null);          
            
            //rotate BitMap bm1,2,3
            Matrix matrix = new Matrix();
            matrix.setRotate(45);
            
            rotatBm1 = Bitmap.createBitmap(bm1, 0,0,  bm1.getWidth(), bm1.getHeight(), matrix, false);            
            rotatBm2 = Bitmap.createBitmap(bm2, 0,0,  bm2.getWidth(), bm2.getHeight(), matrix, false);            
            rotatBm3 = Bitmap.createBitmap(bm3, 0,0,  bm3.getWidth(), bm3.getHeight(), matrix, false);
            
            //locate bm1,2,3 on background canvas
            float x = backBMap.getWidth()/3,y = backBMap.getHeight()/3;
            canvas.drawBitmap(rotatBm1, x, y, null);
            canvas.drawBitmap(rotatBm2, 2*x, y, null);
            canvas.drawBitmap(rotatBm3, x,2*y, null);
           
            i.setImageBitmap(bmOverlay);            
		  } catch (Exception e) {
			e.printStackTrace();
		  }
	    
		// Add the ImageView to the layout and set the layout as the content view
		mLinearLayout.addView(i);
		setContentView(mLinearLayout);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.album_single_page, menu);
		return true;
	}

}
