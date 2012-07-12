package com.qburst.eshop.product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qburst.eshop.database.DBAdapter;
import com.qburst.eshop.pojo.Product;
import com.qburst.eshop.supportclass.CustomHttpClient;

public class ShowBasketActivity extends ListActivity  {
	public static final String PREFS_NAME = "UserFile";
	ArrayList<HashMap<String, ?>> data = new ArrayList<HashMap<String, ?>>();
	ItemsAdapter adapter;
	List<Product> productObjList = null;
	String userId;
	/** Gets details of products in basket from web service */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    String productIdList = " ";
	    String _EMPTY = " ";
	    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
	    userId = sharedPreferences.getString("userId", "");
	    String productResponse1 =null;
	    	        
	    DBAdapter db = new DBAdapter(ShowBasketActivity.this);
		db.open();
		// Fetches the products in basket
		Cursor c = db.fetchBasket(userId.trim());
		if (c == null) {
		} 
		else {
			if (c.getCount() > 0) {
				if (c.moveToFirst()) {
					do {
						String productId = c.getString(1);
						if(productIdList.equals(_EMPTY)){
							productIdList = productId;
						}
						else{
						productIdList  = productIdList+","+productId ;
						}} while (c.moveToNext());
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();		
					postParameters.add(new BasicNameValuePair("item",productIdList));
					// Gets the details of product in basket from web server  
					try {
						productResponse1 = CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/GetProductDetails", postParameters);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					 db.close();
					// Parse the response string to list of product object 
					Type productType = new TypeToken<List<Product>>(){}.getType();
			        String s1 =productResponse1.toString();
					productObjList = new Gson().fromJson(s1,productType);
					// Inflate the list with product details
					this.adapter = new ItemsAdapter(ShowBasketActivity.this, R.layout.wishlistlayout,0, productObjList );
			        setListAdapter(this.adapter);
				}
				
			}
			else
		      	  Toast.makeText(getBaseContext(),"No products in Basket", Toast.LENGTH_LONG).show(); 
		}
	}
	
	private class ItemsAdapter extends ArrayAdapter<Product> {
		private List<Product> productObjList1;
		public ItemsAdapter(Context context, int resource,
				int textViewResourceId, List<Product> objects) {
			super(context, resource, textViewResourceId, objects);
			
			this.productObjList1 = objects;
		}
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.wishlistlayout, null);
            }
                // Gets the object of selected product
                Product product =  productObjList1.get(position);
                if (product != null) {
                    ImageView productImage = (ImageView) v.findViewById(R.id.productimage);
                    TextView productName = (TextView) v.findViewById(R.id.product_name);
                    TextView price = (TextView) v.findViewById(R.id.price);

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
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
			String item = Integer.toString(productObjList.get(position).getId());
			String cost = Integer.toString(productObjList.get(position).getPrice());
			Intent intent = new Intent().setClass(ShowBasketActivity.this, ProductDetailsActivity.class); 
		    intent.putExtra("item", item.trim());
		    intent.putExtra("cost", cost.trim());
		    startActivity(intent);

	}
	// Menu option
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
		        				Intent intent = new Intent().setClass(ShowBasketActivity.this, TabWidgetActivity.class); 
		        				startActivity(intent);
	                            break;
		        
		        case R.id.logoutlog:
					        	Intent homeintent = new Intent().setClass(ShowBasketActivity.this, LogoActivity.class); 
			    				startActivity(homeintent);
			    				break;
		    }
		    return true;
		}
}
