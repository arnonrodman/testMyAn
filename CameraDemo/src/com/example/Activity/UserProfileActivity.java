package com.example.Activity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.cameraD.CameraUtils;
import com.example.cameraD.CreateNewAlbumActivity;
import com.example.cameraD.R;

public class UserProfileActivity extends Activity {

	private String email,androidId;
	private Button createNewAlboumButtonFromUSer;
	private Location location;
	private Activity mainActiv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		mainActiv = this;
		email = getIntent().getExtras().getString("email");
		androidId = getIntent().getExtras().getString("androidId");
		
		location = CameraUtils.getMyBestLocation(getApplicationContext());
		List<Address> addresses = null;
		try {
			Geocoder geo = new Geocoder(UserProfileActivity.this.getApplicationContext(), Locale.getDefault());
			addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 10);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		createNewAlboumButtonFromUSer = (Button)findViewById(R.id.createNewAlboumButtonFromUSer);
		createNewAlboumButtonFromUSer.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				LocationListener locationLis = new LocationListener() {
					
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						
					}
				};
				
				Intent startNewActivityOpen = new Intent(mainActiv, CreateNewAlbumActivity.class);
 				startNewActivityOpen.putExtra("email", email); 					
 				mainActiv.startActivityForResult(startNewActivityOpen, 0); 
				
			}
		});
	 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);		
		return true;
	}

}
