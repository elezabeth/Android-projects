package com.qburst.eshop.product;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogoActivity extends Activity {
	public static final String PREFS_NAME = "UserFile";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.logolayout);
	    Button login  = (Button) findViewById(R.id.login);
	    Button withoutlogin = (Button) findViewById(R.id.withoutlogin);
	    // Enter after log in
	    login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
	    // TODO Auto-generated method stub
				Intent intent = new Intent().setClass(getBaseContext(),LoginActivity.class );
				startActivity(intent);
				
			}
	    });
	    // Enter without login
	    withoutlogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
	    // TODO Auto-generated method stub
				SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        	    SharedPreferences.Editor editor = sharedPreferences.edit();
        	    editor.putString("userId","0");
        	    editor.commit();
				Intent intent = new Intent().setClass(getBaseContext(),TabWidgetActivity.class );
				startActivity(intent);
			}
	    });
	    
	}

}
