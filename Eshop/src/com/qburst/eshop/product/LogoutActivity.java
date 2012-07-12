package com.qburst.eshop.product;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class LogoutActivity extends Activity {
	public static final String PREFS_NAME = "UserFile";
	/** Log out user */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putString("userId","");
	    editor.commit();
	    Intent intent = new Intent().setClass(getBaseContext(),LogoActivity.class );
		startActivity(intent);
	}

}
