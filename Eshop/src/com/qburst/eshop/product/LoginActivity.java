package com.qburst.eshop.product;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.qburst.eshop.pojo.User;
import com.qburst.eshop.pojo.UserObject;
import com.qburst.eshop.supportclass.CustomHttpClient;

public class LoginActivity extends Activity {
	EditText userId;
	EditText password;
	Button signIn;
	Button register;
	User user;
	UserObject userObject;
	public static final String PREFS_NAME = "UserFile";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	setContentView(R.layout.loginlayout);
	userId = (EditText) findViewById(R.id.userid);
	password = (EditText) findViewById(R.id.password);
	signIn = (Button) findViewById(R.id.signin);
	register = (Button)findViewById(R.id.register);
	
	    // TODO Auto-generated method stub
	signIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				String userIdValue = userId.getText().toString().trim();
				String passwordValue = password.getText().toString().trim();
				String response = null;
				String _VALIDATE = "failure";
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		        postParameters.add(new BasicNameValuePair("userid", userIdValue));
		        postParameters.add(new BasicNameValuePair("password",passwordValue));
		        int flag = 0;
		        // Validate the edit text fields
		        if(userIdValue.length() == 0){
		        	userId.setError("Enter user id");
		        	flag = 1;
		        }
		        if(passwordValue.length() == 0){
		        	password.setError("Enter password");
		        	flag = 1;
		        }
		        if (flag==0){
	            try {
					response = CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/VerifyLogin", postParameters);
					_VALIDATE = response;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            // if user details failed , the user cannot log in
	         	if(response.trim().equals("failure")){
	         		 Toast.makeText(getBaseContext(),"Invalid username or password", Toast.LENGTH_LONG).show();
	         		 SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
	         	     SharedPreferences.Editor editor = sharedPreferences.edit();
	         	     editor.putString("userId","0");
	         	     editor.commit();
	         	}
	         	// if the user validated the user logs in
	         	else{
	         		  SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
	         	      SharedPreferences.Editor editor = sharedPreferences.edit();
	         	      editor.putString("userId",response);
	         	      editor.commit();
	         	      password.setText("");
	         		  Intent intent = new Intent().setClass(getBaseContext(),TabWidgetActivity.class );
	 				  startActivity(intent);
	         	}
		        }
		       

			}
	});
	// Redirects to registration page
	register.setOnClickListener(new View.OnClickListener() {
		public void onClick(View arg0) {
			Intent intent = new Intent().setClass(LoginActivity.this, UserRegisterActivity.class); 
			 startActivity(intent);
		}
	});
	}

}
