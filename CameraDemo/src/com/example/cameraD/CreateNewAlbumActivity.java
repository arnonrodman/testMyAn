package com.example.cameraD;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.example.Activity.CameraDemoActivity;


public class CreateNewAlbumActivity extends Activity {

	private Activity currentActivity;
	private Location location;
	private String androidId,email,gridIteamName,calulatedAlbumName;
	private String businessNames,albumLogicalName,addressItem,userLocalAlbumeeeFolder;
	private List<String> business;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_album);
		
		email = this.getIntent().getExtras().getString("email");
		userLocalAlbumeeeFolder = this.getIntent().getExtras().getString("userLocalAlbumeeeFolder");
		
		currentActivity = this;
        GridView gridview = (GridView) findViewById (R.id.grid_view);
 
        // Instance of ImageAdapter Class
        gridview.setAdapter (new ImageAdapter (this));
        new BusinessCalculator().execute(this);
        String[]  address = convertBusinessNamesArray();
        
        if(address != null && address.length != 0){
        	conectListViewToBusinessAroundMe();
        }
        
         /**
         * On Click event for Single Gridview Item
         **/
        gridview.setOnItemClickListener (new OnItemClickListener () {
            @ Override
            public void onItemClick (AdapterView <?> parent, View v,
                    int position, long id) {
 
            	ImageAdapter ia = new ImageAdapter (currentActivity);
            	gridIteamName = ia.images.get (ia.getItem (position));
            	 try {
         			calcualteAlbumName();
         		} catch (Exception e) {
         			// TODO Auto-generated catch block
         			e.printStackTrace();
         		} 	
            }
        });         
        
        EditText textMsg = (EditText)findViewById(R.id.albumLogicalName);
        textMsg.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
            	albumLogicalName = s.toString().trim();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        }); 
        try {
			calcualteAlbumName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Connect the retrieved addresses into list view.
	 */
	public void conectListViewToBusinessAroundMe(){
		ListView listView = (ListView)findViewById(R.id.list_view);
		String[]  address = convertBusinessNamesArray();
    	final AddressAdapter addAdapter = new AddressAdapter(this, address);
    	listView.setAdapter(addAdapter);
    	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    	      @Override
    	      public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {    	    	  
    	    	  addressItem = (String) parent.getItemAtPosition(position);    	        
    	      }
    	}); 
    	
	}
	
	
	/**
	 * This method will calculate the album name and send to server.
	 * @throws Exception
	 */
	private void calcualteAlbumName()throws Exception{
		calulatedAlbumName = null;
		//calculate my address
		List<Address> address = getAdress();
		
		if(gridIteamName!= null){
			calulatedAlbumName = gridIteamName;
			if(addressItem!= null){
				calulatedAlbumName = addressItem;
			}
		}
				
		if(calulatedAlbumName!= null){
			String testUserId = (new BigInteger (130, new SecureRandom ())).toString (32); 
			new NewAlbumAsyncTask(this).execute (
						calulatedAlbumName,
						testUserId,
						String.valueOf(location.getLatitude()),
						String.valueOf(location.getAltitude()),
						androidId,
						email,userLocalAlbumeeeFolder);			
		}		
	}
	
	public void activateCameraDemoActivity(String UPuserLocalAlbumeeeFolder){
		Intent startNewActivityOpen = new Intent(getApplicationContext(), CameraDemoActivity.class);
		startNewActivityOpen.putExtra("email", email);
		startNewActivityOpen.putExtra("newAlbumName", calulatedAlbumName);
		startNewActivityOpen.putExtra("location",location);
		//Albumeee/newAlbumName
		startNewActivityOpen.putExtra("userLocalAlbumeeeFolder",UPuserLocalAlbumeeeFolder);
		
		startActivityForResult(startNewActivityOpen, 0);
	}
	
	/**
	 * Convert return BusinessCalculator address String  list into array.
	 * @return
	 */
	private String[] convertBusinessNamesArray(){
		String element;
		List<String> busiss = new ArrayList<String>();
		if(businessNames!= null){
    		business = new ArrayList<String>();
    		
    		StringTokenizer tokens = new StringTokenizer(businessNames, ",");
    		while(tokens.hasMoreElements()){
    			element = tokens.nextElement().toString().trim();
    			busiss.add(element);
    		}
    	}
		return busiss.toArray(new String[busiss.size()]);
	}
		
	/**
	 * return list address location Latitude and Longitude .
	 * @return
	 * @throws Exception
	 */
	private List<Address>getAdress()throws Exception{
		location = CameraUtils.getMyBestLocation (getApplicationContext ());
        Geocoder geocoder = new Geocoder (this, Locale.getDefault ());
        return geocoder.getFromLocation (location.getLatitude () ,location.getLongitude (), 1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_album, menu);
		return true;
	}


	public String getBusinessNames() {
		return businessNames;
	}


	public void setBusinessNames(String businessNames) {
		this.businessNames = businessNames;
	}

}
