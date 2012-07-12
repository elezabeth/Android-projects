package com.qburst.eshop.product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qburst.eshop.database.DBAdapter;
import com.qburst.eshop.facebook.TestConnect;
import com.qburst.eshop.pojo.ClassObject;
import com.qburst.eshop.pojo.Product;
import com.qburst.eshop.supportclass.CustomHttpClient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DailyPurcahseActivity extends Activity implements OnItemClickListener  {
	public static final String PREFS_NAME = "UserFile";
	List<Product> productObjList;
	static ItemsAdapter adapter;
	String[] purchaseIdList= new String[50];
	ArrayList<HashMap<String, ?>> data = new ArrayList<HashMap<String, ?>>();
	HashMap<String, Object> row;
	ListView listView;
	static final int DATE_DIALOG_ID = 0;
	EditText displayDate ;
	private int mYear;
    private int mMonth;
    private int mDay;
	/**Displays the purchase details on a particular day */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    	row  = new HashMap<String, Object>();
	    	setContentView(R.layout.dailypurchaselayout);
	    	listView = (ListView) findViewById(R.id.dailypurchaselist);
	    	Button pickDate = (Button) findViewById(R.id.pickdate);
	    	displayDate = (EditText) findViewById(R.id.displaydate);
	    	// Displays a dialog box to pick date
	    	pickDate.setOnClickListener(new View.OnClickListener() {
	    		@Override
	    		public void onClick(View v) { 
	    			showDialog(DATE_DIALOG_ID);
	    		}
	    	});
        
	    	final Calendar c = Calendar.getInstance();
	    	mYear = c.get(Calendar.YEAR);
	    	mMonth = c.get(Calendar.MONTH);
	    	mDay = c.get(Calendar.DAY_OF_MONTH);

	    	// display the current date (this method is below)
	    	updateDisplay();
	    	// On selecting the date the the purchased item on particular day is fetched from database
	    	displayDate.addTextChangedListener(new TextWatcher() {
	
				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					String _EMPTY = " ";
					String productId;
					String date = displayDate.getText().toString();
					SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
				    String userId = sharedPreferences.getString("userId", "");
				    if(userId.equals("0")){
				    	Intent intent = new Intent().setClass(DailyPurcahseActivity.this, LoginActivity.class); 
						startActivity(intent);
				    }
				    else{
					DBAdapter dBAdapter = new DBAdapter(DailyPurcahseActivity.this);
					dBAdapter.open();
					// Fetches product id of particular users particular days purchase from purchase database
					Cursor c = dBAdapter.fetchPurchase(date.trim());
					
					String productResponse1 =null;
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					
					String productIdList = " ";
					int i =0;
					if (c == null) {
						// do nothing
					} 
					else {
						if (c.getCount() > 0) {
							if (c.moveToFirst()) {
								do {
									productId = c.getString(1);
									purchaseIdList[i] = c.getString(3);
									i++;
									if(productIdList.equals(_EMPTY)){
										productIdList = productId;
									}
									else{
										productIdList  = productIdList+","+productId ;
									}
							
								} while (c.moveToNext());
								postParameters.add(new BasicNameValuePair("item",productIdList));
								// Fetches the product details from web service
								try {
									// Fetches product details from web server
									productResponse1 = CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/GetProductDetails", postParameters);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								
								Type productType = new TypeToken<List<Product>>(){}.getType();
							    String s1 =productResponse1.toString();
							    productObjList = new Gson().fromJson(s1,productType);
							    dBAdapter.close();
							    // Displays product details in a listview
							    listView.setOnItemClickListener(DailyPurcahseActivity.this);
								DailyPurcahseActivity.adapter = new ItemsAdapter(DailyPurcahseActivity.this, R.layout.wishlistlayout,0, productObjList );
								listView.setAdapter(DailyPurcahseActivity.adapter);
								
							}
						}
						else
					      	  Toast.makeText(DailyPurcahseActivity.this,"No items purchased", Toast.LENGTH_LONG).show(); 
					}
				    }
				}
	        });
    
	}
	private class ItemsAdapter extends ArrayAdapter<Product> {
		private List<Product> productObjList1;
		public ItemsAdapter(Context context, int resource,
				int textViewResourceId, List<Product> objects) {
			super(context, resource, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			this.productObjList1 = objects;
		}
		// Fills the list view with product details
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.dailypurchasecustomlist, null);
            }
                Product product =  productObjList1.get(position);
                if (product != null) {
                    ImageView productImage = (ImageView) v.findViewById(R.id.purchasedimage);
                    TextView productName = (TextView) v.findViewById(R.id.purchasedname);
                    TextView price = (TextView) v.findViewById(R.id.purchasedcost);
                    TextView purchaseId = (TextView) v.findViewById(R.id.purchaseid);
                    if (productImage != null) {
                    	Bitmap bp = BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length);
                    	productImage.setImageBitmap(bp);
                    	productName.setText(product.getName());
                    	price.setText(Integer.toString(product.getPrice()));
                    	purchaseId.setText(purchaseIdList[position]);
                    }
            }
            return v;
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
	    private DatePickerDialog.OnDateSetListener mDateSetListener =
	            new DatePickerDialog.OnDateSetListener() {

	                public void onDateSet(DatePicker view, int year, 
	                                      int monthOfYear, int dayOfMonth) {
	                    mYear = year;
	                    mMonth = monthOfYear;
	                    mDay = dayOfMonth;
	                    updateDisplay();
	                }
	            };
	            @Override
	            protected Dialog onCreateDialog(int id) {
	                switch (id) {
	                case DATE_DIALOG_ID:
	                    return new DatePickerDialog(this,
	                                mDateSetListener,
	                                mYear, mMonth, mDay);
	                }
	                return null;
	            }
	        // Displays the menu    
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
	  		        				Intent intent = new Intent().setClass(DailyPurcahseActivity.this, TabWidgetActivity.class); 
	  		        				startActivity(intent);
	  	                            break;
	  		        
	  		        case R.id.logoutlog:
	  					        	Intent homeintent = new Intent().setClass(DailyPurcahseActivity.this, LogoActivity.class); 
	  			    				startActivity(homeintent);
	  			    				break;
	  		    }
	  		    return true;
	  		}
	  		
	  		@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				   System.out.println("on click");
				   ClassObject classObj = ClassObject.getClassObject();
	    		   Product product = productObjList.get(position);
	    		   classObj.setProduct(product);
	    		   Intent intent = new Intent().setClass(DailyPurcahseActivity.this, PrintBillActivity.class);
	    		   intent.putExtra("purchaseId", purchaseIdList[position]);
	    		   startActivity(intent);
	    	        
	  	        }
}
