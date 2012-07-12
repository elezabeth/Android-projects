package com.qburst.eshop.product;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.qburst.eshop.database.DBAdapter;
import com.qburst.eshop.supportclass.CustomHttpClient;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PaymentDetailsActivity extends Activity {
	String item;
	String cost;
	public static final String PREFS_NAME = "UserFile";
	/** User enters the payment details*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	setContentView(R.layout.paymentdetailslayout);
	Button paybutton = (Button) findViewById(R.id.paybutton);
	Bundle responses = getIntent().getExtras();
    item = responses.getString("item");
    cost = responses.getString("cost");
	
	paybutton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			EditText name = (EditText) findViewById(R.id.name);
			EditText ccn = (EditText) findViewById(R.id.ccn);
			EditText date = (EditText) findViewById(R.id.date);
			EditText cvv = (EditText) findViewById(R.id.cvv);
			String nameValue = name.getText().toString().trim();
			String ccnValue = ccn.getText().toString().trim();
			String dateValue = date.getText().toString().trim();
			String cvvValue = cvv.getText().toString().trim();
			int flag =0;
			// Validate the edit text fields
			if(nameValue.length() == 0){
				name.setError("Enter name");
				flag = 1;
			}
			if(ccnValue.length() == 0){
				ccn.setError("Enter credit card number");
				flag =1;
			}
			if(dateValue.length() == 0){
				date.setError("Enter Expiry date");
				flag =1;
			}
			if(cvvValue.length() == 0 || !(cvvValue.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))){
				cvv.setError("Enter CVV number");
				flag = 1;
			}
			if(flag ==0){
			SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
		    String userId = sharedPreferences.getString("userId", "");
			String response = null;
			String _FAILURE = "failure";
			// Sends the user details to web server for updation
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		    postParameters.add(new BasicNameValuePair("id",userId));
		    postParameters.add(new BasicNameValuePair("name",nameValue));
		    postParameters.add(new BasicNameValuePair("ccn",ccnValue));
		    postParameters.add(new BasicNameValuePair("date",dateValue));
		    postParameters.add(new BasicNameValuePair("cvv",cvvValue));
		    postParameters.add(new BasicNameValuePair("cost",cost));
		    
		    try {
				response = CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/VerifyCredentialsServlet", postParameters);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if(response.trim().equals("failure"))
		    	Toast.makeText(getBaseContext(),"Your login credentials are not valid",Toast.LENGTH_LONG ).show();
		    else{
		    	Toast.makeText(getBaseContext(),"Your purchase id is "+response,Toast.LENGTH_LONG ).show();
		   
				DBAdapter db = new DBAdapter(PaymentDetailsActivity.this);
				db.open();
				// Insert into Basket
				Time today = new Time(Time.getCurrentTimezone());
				today.setToNow();
				
				String dateToday = today.month+"-"+today.monthDay+"-"+today.year;
				
				// If payment success, insert the purchase details to database
				db.insertPurchase(userId,item,dateToday.trim(),response.trim());
				db.close();
		    }
			}
		}
	});
	}
	  // Menu option
	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.logmenu,menu);
		    return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		        case R.id.homelog: 
		        				Intent intent = new Intent().setClass(PaymentDetailsActivity.this, TabWidgetActivity.class); 
		        				startActivity(intent);
	                            break;
		        
		        case R.id.logoutlog:
					        	Intent homeintent = new Intent().setClass(PaymentDetailsActivity.this, LogoActivity.class); 
			    				startActivity(homeintent);
			    				break;
		    }
		    return true;
		}
}
