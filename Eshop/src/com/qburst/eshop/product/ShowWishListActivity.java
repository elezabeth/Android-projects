package com.qburst.eshop.product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qburst.eshop.database.DBAdapter;
import com.qburst.eshop.facebook.TestConnect;
import com.qburst.eshop.pojo.Product;
import com.qburst.eshop.supportclass.CustomHttpClient;

/**
 *  Gets details of products in wish list from web service
 *
 */
public class ShowWishListActivity extends Activity implements OnItemClickListener  {
	ArrayList<HashMap<String, ?>> data;
	public static final String PREFS_NAME = "UserFile";
	String productResponse1 =null;
	List<Product> productObjList = null;
	

	CheckBox checkShowlist;
	ListView listView;
	protected boolean[] checkStates;

	ItemsAdapter adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.testwishlist);
	    String productIdList = " ";
	    String _Empty = " ";
	    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
	    String userId = sharedPreferences.getString("userId", "");
	    DBAdapter db = new DBAdapter(ShowWishListActivity.this);
		db.open();
		Cursor c = db.fetchWishlist(userId.trim());
		if (c == null) {
			// do nothing
		} 
		// Fetches the product id from wishlist database
		else {
			if (c.getCount() > 0) {

				if (c.moveToFirst()) {
					do {
						String productId = c.getString(1);
						if(productIdList.equals(_Empty)){
							productIdList = productId;
						}
						else{
						productIdList  = productIdList+","+productId ;
						}
					} while (c.moveToNext());
				// Gets the details of product from webserver	
				postParameters.add(new BasicNameValuePair("item",productIdList));
				try {
					productResponse1 = CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/GetProductDetails", postParameters);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				db.close();
				// Parse the response string to product objects list
				Type productType = new TypeToken<List<Product>>(){}.getType();
		        String s1 =productResponse1.toString();
		        productObjList = new Gson().fromJson(s1,productType);
		        checkStates = new boolean[productObjList.size()];
		        listView = (ListView) findViewById(R.id.testlist);
		        listView.setOnItemClickListener(this);
		        
		        this.adapter = new ItemsAdapter(ShowWishListActivity.this, R.layout.wishlistlayout,0, productObjList );
		        listView.setAdapter(this.adapter);
					}
				}
			else
		      	  Toast.makeText(getBaseContext(),"No products in wishlist", Toast.LENGTH_LONG).show(); 
			}
		
	}
	
	private class ItemsAdapter extends ArrayAdapter<Product> {
		private List<Product> productObjList1;
		public ItemsAdapter(Context context, int resource,
				int textViewResourceId, List<Product> objects) {
			super(context, resource, textViewResourceId, objects);
			this.productObjList1 = objects;
		}
		/**
		 *  Inflate each row of list with product details
		 *
		 */
		// Populate the list with product details
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.wishlistlayout, null);
            }
                Product product =  productObjList1.get(position);
                if (product != null) {
                    ImageView productImage = (ImageView) v.findViewById(R.id.productimage);
                    TextView productName = (TextView) v.findViewById(R.id.product_name);
                    TextView price = (TextView) v.findViewById(R.id.price);
                    checkShowlist = (CheckBox) v.findViewById(R.id.check_wishlist);
                    checkShowlist.setTag(position);
                   
                    checkShowlist.setChecked(checkStates[position]);
                    
					checkShowlist.setOnCheckedChangeListener(new OnCheckedChangeListener()
					{
					    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
					    {
					        if ( isChecked )
					        {
					        	 final Integer position = (Integer) buttonView.getTag();
					             checkStates[position] = isChecked;
					
					        }
					
					    }
					});
                    if (productImage != null) {
                    	Bitmap bp = BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length);
                    	productImage.setImageBitmap(bp);
                    	productName.setText(product.getName());
                    	price.setText(Integer.toString(product.getPrice()));
                    }
            }
            return v;
		}
		

	}
	/**
	 *  Redirects to display the details of particular product on item click
	 *
	 */
	  
	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.wishlistmenu,menu);
		    return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		        case R.id.share: 
		        				Intent intent = new Intent().setClass(ShowWishListActivity.this, TestConnect.class); 
		        				intent.putExtra("wishlist",productResponse1);
		        				startActivity(intent);
	                            break;
		        case R.id.delete:
		        				for(int i =0;i<productObjList.size();i++){
		        				if(checkStates[i]){
		        					String deleteProductId = Integer.toString(productObjList.get(i).getId());
		        					DBAdapter dbAdapter = new DBAdapter(ShowWishListActivity.this);
		        					dbAdapter.open();
		        					dbAdapter.deleteWishlist(deleteProductId);
		        					dbAdapter.close();
		        				}
		        				}
	                            break;
		        case R.id.home:
					        	Intent homeintent = new Intent().setClass(ShowWishListActivity.this, TabWidgetActivity.class); 
			    				startActivity(homeintent);
			    				break;
		        case R.id.logout:
					        	Intent logoutintent = new Intent().setClass(ShowWishListActivity.this, LogoActivity.class); 
			    				startActivity(logoutintent);
			    				break;
		    }
		    return true;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			 System.out.println("here on click");
  		    String item = Integer.toString(productObjList.get(position).getId());
 			String cost = Integer.toString(productObjList.get(position).getPrice());
 			Intent intent = new Intent().setClass(ShowWishListActivity.this, ProductDetailsActivity.class); 
 		    intent.putExtra("item", item.trim());
 		    intent.putExtra("cost", cost.trim());
 		    startActivity(intent);
  	        }
  	        public void onNothingSelected(AdapterView parentView) {  

  	        }  
			
		
}
