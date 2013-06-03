package com.example.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cameraD.R;
import com.example.cameraD.ValidateNewUser;

public class LoginActivity extends Activity {
	private EditText un,pw;
	private Button 	 ok,register;
	public  Boolean  retunnumfromAsyncTask;
	private static   Activity myActivity;
	private String   androidId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		myActivity = this;
		un=(EditText)findViewById(R.id.et_un);
		pw=(EditText)findViewById(R.id.et_pw);
		ok=(Button)findViewById(R.id.btn_login);		
		androidId = Secure.getString(getBaseContext().getContentResolver(),Secure.ANDROID_ID);
				
		List<ApplicationInfo> localApplications = this.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA); 
		StringBuffer apps = new StringBuffer(); 
		List<String> cameraPackageName =  new ArrayList<String>();
		String instagramPName = null;
		for(ApplicationInfo appa:localApplications){
			if(appa.packageName.trim().contains("instagram")){
				cameraPackageName.add(appa.packageName);
				instagramPName = appa.packageName.trim();
			}
			
			apps.append(appa.packageName).append("\n");
		}
		//GridLayout grid = (GridLayout)findViewById(R.id.grid);
		if(instagramPName!=null){			
			Intent LaunchApp = getPackageManager().getLaunchIntentForPackage(instagramPName);
			startActivity( LaunchApp );
		}
			
		
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String userName,password;
				userName = un.getText().toString().trim();
				password = pw.getText().toString().trim();
				new ValidateNewUser(myActivity).execute(userName, password,androidId);
			}		
		 });
		
		register=(Button)findViewById(R.id.btn_Register);
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent startNewActivityOpen = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivityForResult(startNewActivityOpen, 0);				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	
}
