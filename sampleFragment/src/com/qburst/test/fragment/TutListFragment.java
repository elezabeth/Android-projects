package com.qburst.test.fragment;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;

public class TutListFragment extends ListFragment {
	private OnTutSelectedListener tutSelectedListener;
	public interface OnTutSelectedListener {
	    public void onTutSelected(String tutUri);
	}
	private int mYear;
    private int mMonth;
    private int mDay;
    Activity activity;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setListAdapter(ArrayAdapter.createFromResource(getActivity()
	            .getApplicationContext(), R.array.tut_titles,
	            R.layout.list_item));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	    /*String[] links = getResources().getStringArray(R.array.tut_links);
	 
	    String content = links[position];
	    Intent showContent = new Intent(getActivity().getApplicationContext(),
	            SampleFragmentActivity2.class);
	    showContent.setData(Uri.parse(content));
	    startActivity(showContent);*/
//		String[] links = getResources().getStringArray(R.array.tut_links);
//		 
//	    String content = links[position];
//	   
//	    tutSelectedListener.onTutSelected(content);
		activity.showDialog(0);
	}
	@Override
	public void onAttach(Activity activity) {
	super.onAttach(activity);
	this.activity = activity;
	try {
		
		
	tutSelectedListener = (OnTutSelectedListener) activity;
	
	} catch (ClassCastException e) {
	throw new ClassCastException();
	}
	}
	private void updateDisplay() {
		 StringBuilder s =
	            new StringBuilder()
	                    // Month is 0 based so add 1
	                    .append(mMonth + 1).append("-")
	                    .append(mDay).append("-")
	                    .append(mYear).append(" ");
	    }
	 // the callback received when the user "sets" the date in the dialog
	   
}	
