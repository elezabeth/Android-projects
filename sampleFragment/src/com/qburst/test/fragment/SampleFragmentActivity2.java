package com.qburst.test.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class SampleFragmentActivity2 extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.tutview_fragment);
	    
	    Intent launchingIntent = getIntent();
	    String content = launchingIntent.getData().toString();
	 
	    TutViewerFragment viewer = (TutViewerFragment) getFragmentManager()
	            .findFragmentById(R.id.tutview_fragment);
	 
	    viewer.updateUrl(content);
	     
	       /* Intent launchingIntent = getIntent();
	        String content = launchingIntent.getData().toString();
	 
	        WebView viewer = (WebView) findViewById(R.id.tutView);
	        viewer.loadUrl(content);*/
	        }
}
