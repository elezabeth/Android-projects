package com.qburst.eshop.product;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.qburst.eshop.pojo.User;
import com.qburst.eshop.pojo.UserObject;
import com.qburst.eshop.supportclass.CustomHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MyProfileActivity extends Activity {
	public static final String PREFS_NAME = "UserFile";
	String userId;
	TextView name;
	TextView emailId ;
	TextView phonenumber ;
	User user;
	/** Displays the profile details of the user */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
	    userId = sharedPreferences.getString("userId", "");
	    if(userId.equals("0")){
	    	Intent intent = new Intent().setClass(MyProfileActivity.this, LoginActivity.class); 
			startActivity(intent);
	    }
	    else{
        setContentView(R.layout.myprofile);
        name = (TextView) findViewById(R.id.namedisplay);
        emailId = (TextView) findViewById(R.id.emaildisplay);
        phonenumber = (TextView) findViewById(R.id.phonedisplay);
        Button edit = (Button) findViewById(R.id.editprofile);
        String response = null;
        
        // Fetches the user details from web service
	    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("userId", userId.trim()));
	    try {
	    	response = CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/GetUser", postParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    user = new Gson().fromJson(response,User.class);
	    name.setText(user.getName());
	    emailId.setText(user.getEmailAddress());
	    phonenumber.setText(user.getPhoneNumber());
	    
	    // Redirects to user profile edit page
	    edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserObject userObj = UserObject.getUserObject();
				userObj.setUser(user);
				System.out.println(user.getEmailAddress());
				Intent intent = new Intent().setClass(MyProfileActivity.this, EditProfileActivity.class); 
				intent.putExtra("userId", userId.trim());
				startActivity(intent);

	}});
	    }
	}

}
