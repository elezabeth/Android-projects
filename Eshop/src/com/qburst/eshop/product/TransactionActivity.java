package com.qburst.eshop.product;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class TransactionActivity extends Activity {
	 String res;
	 String userId;
		public static final String PREFS_NAME = "UserFile";
	/** Displays wishlist and basket */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactionlayout);
	}
	public void wishlistClick(View view){
		SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
		userId = sharedPreferences.getString("userId", "");
	    if(userId.equals("0")){
	    	Intent intent = new Intent().setClass(this, LoginActivity.class); 
			startActivity(intent);
	    }
	    else{
			Intent intent = new Intent().setClass(this, ShowWishListActivity.class); 
			startActivity(intent);
	    }
	}
	public void basketClick(View view){
		SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
	    String userId = sharedPreferences.getString("userId", "");
	    if(userId.equals("0")){
	    	Intent intent = new Intent().setClass(this, LoginActivity.class); 
			startActivity(intent);
	    }
	    else{
			Intent intent = new Intent().setClass(this, ShowBasketActivity.class); 
			startActivity(intent);
	    }
	}
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
		        				Intent intent = new Intent().setClass(TransactionActivity.this, TabWidgetActivity.class); 
		        				startActivity(intent);
	                            break;
		        
		        case R.id.logoutlog:
		        				
					        	Intent homeintent = new Intent().setClass(TransactionActivity.this, LogoActivity.class); 
			    				startActivity(homeintent);
		        				
			    				break;
		    }
		    return true;
		}
}
