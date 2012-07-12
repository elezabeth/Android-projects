package com.qburst.eshop.product;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;

public class TabWidgetActivity extends TabActivity{
	ImageView book;
	ImageView antique;
	ImageView shoe;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    Resources res = getResources(); 
	    TabHost tabHost = getTabHost(); 
	    TabHost.TabSpec spec;  
	    Intent intent;  

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, HomeActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("home").setIndicator("Home",
	                      res.getDrawable(R.drawable.homeicon))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, TransactionActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("transaction").setIndicator("Transaction",
	                      res.getDrawable(R.drawable.transaction))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, DailyPurcahseActivity.class);
	    spec = tabHost.newTabSpec("dailypurchase").setIndicator("Daily Purchse",
	                      res.getDrawable(R.drawable.payment))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    
	    intent = new Intent().setClass(this, MyProfileActivity.class);
	    spec = tabHost.newTabSpec("myProfile").setIndicator("My Profile",
	                      res.getDrawable(R.drawable.iconprofile))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    tabHost.setCurrentTab(0);
	}
	 
}
