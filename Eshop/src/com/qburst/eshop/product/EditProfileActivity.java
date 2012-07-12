package com.qburst.eshop.product;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.qburst.eshop.pojo.User;
import com.qburst.eshop.pojo.UserObject;
import com.qburst.eshop.supportclass.CustomHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfileActivity extends Activity {
	EditText name;
	EditText emailId ;
	EditText phoneNumber ;
	String userId;
	User user;
	/** Edit the profile details of user */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.editprofile);
	    Button update = (Button) findViewById(R.id.update);
	    name = (EditText) findViewById(R.id.editname);
        emailId = (EditText) findViewById(R.id.editaddress);
        phoneNumber = (EditText) findViewById(R.id.editphone);
        UserObject userObj = UserObject.getUserObject();
        user = userObj.getUser();
        System.out.println(user.getEmailAddress());
        // gets the user details
        name.setText(user.getName());
        emailId.setText(user.getEmailAddress());
        phoneNumber.setText(user.getPhoneNumber());
        Bundle responses = getIntent().getExtras();
        userId = responses.getString("userId");
        update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<NameValuePair> postParameters1 = new ArrayList<NameValuePair>();
		        postParameters1.add(new BasicNameValuePair("userId", userId.trim()));
		        postParameters1.add(new BasicNameValuePair("name", name.getText().toString().trim()));
		        postParameters1.add(new BasicNameValuePair("email",emailId.getText().toString().trim()));
		        postParameters1.add(new BasicNameValuePair("phonenumber", phoneNumber.getText().toString().trim()));
		        postParameters1.add(new BasicNameValuePair("password", user.getPassword()));
		        // Sends the user details to web service for updating
				try {
					CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/UpdateUser", postParameters1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent().setClass(EditProfileActivity.this, MyProfileActivity.class); 
				startActivity(intent);
			}
});
	}

}
