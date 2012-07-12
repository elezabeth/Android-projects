package com.qburst.eshop.product;




import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.qburst.eshop.supportclass.CustomHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

public class UserRegisterActivity extends Activity implements OnDrawerOpenListener,
OnDrawerCloseListener{
	EditText name;
	EditText address;
	EditText zipcode;
	EditText city;
	EditText country;
	EditText phoneNumber;
	EditText email;
	EditText password;
	EditText repassword;
	EditText dob;
	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	TextView t5;
	TextView t6;
	TextView t7;
	Button submit;
	String nameValue;
    String addressValue;
    String zipvalue;
    String cityValue;
    String countryValue;
    String phoneNumberValue;
    String emailValue;
    String passwordValue;
    String repasswordValue;
    String dobValue;
    String response=null;
    SlidingDrawer slidingDrawer;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    
	    setContentView(R.layout.userregisterlayout);
	    name = (EditText) findViewById(R.id.name);
	    address = (EditText) findViewById(R.id.address);
	    zipcode = (EditText) findViewById(R.id.zipcode);
	    city = (EditText) findViewById(R.id.city);
	    country = (EditText) findViewById(R.id.country);
	    phoneNumber = (EditText) findViewById(R.id.phonenumber);
	    email = (EditText) findViewById(R.id.email);
	    password = (EditText) findViewById(R.id.password);
	    repassword =(EditText) findViewById(R.id.repassword);
	    dob = (EditText) findViewById(R.id.dob);
	    t1 = (TextView) findViewById(R.id.textView1);
	    t2 = (TextView) findViewById(R.id.textView2);
	    t3 = (TextView) findViewById(R.id.textView3);
	    t4 = (TextView) findViewById(R.id.textView4);
	    t5 = (TextView) findViewById(R.id.textView5);
	    t6 = (TextView) findViewById(R.id.textView6);
	    t7 = (TextView) findViewById(R.id.textView7);
	    submit = (Button) findViewById(R.id.submit);
	    slidingDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer1);
	    slidingDrawer.setOnDrawerOpenListener(this);
		    // TODO Auto-generated method stub
	    slidingDrawer.setOnDrawerCloseListener(this);
	    // TODO Auto-generated method stub
	    submit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				nameValue = name.getText().toString().trim();
				addressValue = address.getText().toString().trim();
				zipvalue = zipcode.getText().toString().trim();
				cityValue = city.getText().toString().trim();
				countryValue = country.getText().toString().trim();
				phoneNumberValue = phoneNumber.getText().toString().trim();
				emailValue = email.getText().toString().trim();
				passwordValue = password.getText().toString().trim();
				repasswordValue = repassword.getText().toString().trim();
				dobValue = dob.getText().toString().trim();
				int flag =0;
				// Validate the edit text field
				if(nameValue.length() == 0){
					name.setError("Enter Name");
					slidingDrawer.close();
					flag =1;
				}
				if(addressValue.length() == 0){
					address.setError("Enter Address");
					slidingDrawer.close();
					flag =1;
				}
				if(zipvalue.length() == 0){
					zipcode.setError("Enter Zip code");
					slidingDrawer.close();
					flag =1;
				}
				if(cityValue.length() == 0){
					city.setError("Enter City");
					slidingDrawer.close();
					flag =1;
				}
				if(countryValue.length() == 0){
					country.setError("Enter Country");
					slidingDrawer.close();
					flag =1;
				}
				if(phoneNumberValue.length() == 0){
					name.setError("Enter Phone number");
					slidingDrawer.close();
					flag =1;
				}
				if(emailValue.length() == 0){
					email.setError("Enter Email address");
					flag =1;
				}
				if(passwordValue.length() == 0){
					password.setError("Enter password");
					flag =1;
				}
				if(repasswordValue.length() == 0 || !(repasswordValue.equals(passwordValue))){
					repassword.setError("Password mismatch");
					flag =1;
				}
				if(dobValue.length() == 0){
					dob.setError("Enter Date of birth");
					flag =1;
				}
				if(flag == 0){
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		        postParameters.add(new BasicNameValuePair("name", nameValue));
		        postParameters.add(new BasicNameValuePair("phonenumber", phoneNumberValue));
		        postParameters.add(new BasicNameValuePair("email", emailValue));
		        postParameters.add(new BasicNameValuePair("password", passwordValue));
		        // Validate the user details
	            try {
					response = CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/RegisterUser", postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
	            if (response.trim().equals("ok")){
	            	Toast.makeText(getBaseContext(),"Registered user", Toast.LENGTH_LONG).show();
					Intent intent = new Intent().setClass(getBaseContext(),LoginActivity.class );
					startActivity(intent);

	            }
	            else{
	            	Toast.makeText(getBaseContext(),"Sorry Try Again", Toast.LENGTH_LONG).show();
	            }
	           

				}
				 else
		            	Toast.makeText(getBaseContext(),"Enter values", Toast.LENGTH_LONG).show();
			}
	    });
	}

	@Override
	public void onDrawerClosed() {
		// TODO Auto-generated method stub
		name.setVisibility(View.VISIBLE);
		address.setVisibility(View.VISIBLE);
		zipcode.setVisibility(View.VISIBLE);
		city.setVisibility(View.VISIBLE);
		country.setVisibility(View.VISIBLE);
		phoneNumber.setVisibility(View.VISIBLE);
		t1.setVisibility(View.VISIBLE);
		t2.setVisibility(View.VISIBLE);
		t3.setVisibility(View.VISIBLE);
		t4.setVisibility(View.VISIBLE);
		t5.setVisibility(View.VISIBLE);
		t6.setVisibility(View.VISIBLE);
		t7.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDrawerOpened() {
		// TODO Auto-generated method stub
		name.setVisibility(View.GONE);
		address.setVisibility(View.GONE);
		zipcode.setVisibility(View.GONE);
		city.setVisibility(View.GONE);
		country.setVisibility(View.GONE);
		phoneNumber.setVisibility(View.GONE);
		t1.setVisibility(View.GONE);
		t2.setVisibility(View.GONE);
		t3.setVisibility(View.GONE);
		t4.setVisibility(View.GONE);
		t5.setVisibility(View.GONE);
		t6.setVisibility(View.GONE);
		t7.setVisibility(View.GONE);
	}

}
