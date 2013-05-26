package com.example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cameraD.R;
import com.example.cameraD.RegisterNewUser;

public class RegisterActivity extends Activity {
	private EditText et_emailAddress,et_password;
	private Button  btn_register,btn_back;
	private String  androidId;
	private static  Activity myActivity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		myActivity = this;
		androidId = Secure.getString(getBaseContext().getContentResolver(),Secure.ANDROID_ID);		
		et_emailAddress = (EditText)findViewById(R.id.emailAddress);
		et_password  = (EditText)findViewById(R.id.password);
		btn_register = (Button)findViewById(R.id.register);
		btn_back = (Button)findViewById(R.id.back);
		
		
		btn_register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new RegisterNewUser(myActivity).execute(androidId,et_emailAddress.getText().toString().trim(),et_password.getText().toString().trim());
				
			}
		});
		
		btn_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);				
			}
		});
		
	}
	
	

}
