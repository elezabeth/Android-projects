package com.qburst.test.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

public class SampleFragmentActivity1 extends Activity  implements
TutListFragment.OnTutSelectedListener{
    /** Called when the activity is first created. */
	private int mYear;
    private int mMonth;
    private int mDay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutlist_fragment);
        /*setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.tut_titles, R.layout.list_item));
        final String[] links = getResources().getStringArray(R.array.tut_links);
    getListView().setOnItemClickListener(new OnItemClickListener() {
    	 
        @Override
        public void onItemClick(AdapterView<?> parent, View view,	
                int position, long id) {
        	String content = links[position];
            Intent showContent = new Intent(getApplicationContext(),
                   SampleFragmentActivity2.class);
            showContent.setData(Uri.parse(content));
            startActivity(showContent);
        }
    });*/
    }

	@Override
	public void onTutSelected(String tutUri) {
		 TutViewerFragment viewer = (TutViewerFragment) getFragmentManager()
		            .findFragmentById(R.id.tutview_fragment);
		 
		    if (viewer == null || !viewer.isInLayout()) {
		        Intent showContent = new Intent(getApplicationContext(),
		                SampleFragmentActivity2.class);
		        showContent.setData(Uri.parse(tutUri));
		        startActivity(showContent);
		    } else {
		        viewer.updateUrl(tutUri);
		    }
		
	}
	
	 private DatePickerDialog.OnDateSetListener mDateSetListener =
	            new DatePickerDialog.OnDateSetListener() {

	                public void onDateSet(DatePicker view, int year, 
	                                      int monthOfYear, int dayOfMonth) {
	                    mYear = year;
	                    mMonth = monthOfYear;
	                    mDay = dayOfMonth;
	                    
	                }
	            };
	            @Override
	            protected Dialog onCreateDialog(int id) {
	                switch (id) {
	                case 0:
	                    return new DatePickerDialog(this,
	                                mDateSetListener,
	                                mYear, mMonth, mDay);
	                }
	                return null;
	            }
    
}