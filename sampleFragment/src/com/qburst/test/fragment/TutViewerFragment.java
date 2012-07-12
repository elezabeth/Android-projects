package com.qburst.test.fragment;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class TutViewerFragment extends Fragment {
	WebView viewer;
	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//	    super.onCreate(savedInstanceState);
//	
//	    // TODO Auto-generated method stub
//	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
//	    Intent launchingIntent = getActivity().getIntent();
//	    String content = launchingIntent.getData().toString();
	 
	     viewer = (WebView) inflater.inflate(R.layout.webview_layout, container, false);
//	    viewer.loadUrl(content);
	 
	    return viewer;
	}
	public void updateUrl(String tutUri) {
	    if (viewer != null) {
	       viewer.loadUrl(tutUri);
	    }
	}
}
