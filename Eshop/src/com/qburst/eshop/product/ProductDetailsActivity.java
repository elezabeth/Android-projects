package com.qburst.eshop.product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qburst.eshop.database.DBAdapter;
import com.qburst.eshop.pojo.ClassObject;
import com.qburst.eshop.pojo.Product;
import com.qburst.eshop.supportclass.CheckList;
import com.qburst.eshop.supportclass.CustomHttpClient;

public class ProductDetailsActivity extends Activity {
	String item;
	String cost;
	String userId;
	ProgressDialog mProgress;
	public static final String PREFS_NAME = "UserFile";
	/** Displays the details of a particular product */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle responses = getIntent().getExtras();
	    setContentView(R.layout.productdetails);
	    ClassObject classObj = ClassObject.getClassObject();
	    Product productDetailObj = classObj.getProduct();
	    item = Integer.toString(productDetailObj.getId());
	    cost = Integer.toString(productDetailObj.getPrice());
	    ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("item", item));
        TextView name = (TextView) findViewById(R.id.nameValue);
        TextView price = (TextView) findViewById(R.id.price_value);
        TextView vendor = (TextView) findViewById(R.id.vendor_value);
        TextView comments = (TextView) findViewById(R.id.comment_value);
        Button wishList = (Button) findViewById(R.id.wish_list);
        Button basket = (Button) findViewById(R.id.basket);
        Button buy = (Button) findViewById(R.id.buy);
        ImageView im = (ImageView) findViewById(R.id.imageView1);
        // Inserts the product to wish list
        wishList.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
			    userId = sharedPreferences.getString("userId", "");
			    // Redirects the user to login page if not log in
			    if(userId.equals("0")){
			    	Intent intent = new Intent().setClass(ProductDetailsActivity.this, LoginActivity.class); 
					startActivity(intent);
			    }
			    else{
			    	// Checks if the product already in wishlist
				    CheckList checkList = new CheckList();
				    if(checkList.checkWishlist(ProductDetailsActivity.this, userId.trim(), item)){
						DBAdapter db = new DBAdapter(ProductDetailsActivity.this);
						db.open();
						// Insert into wish list
						db.insertWishlist(userId.trim(),item);
						db.close();
				    }
				    // Redirects to wish list display
					Intent intent = new Intent().setClass(ProductDetailsActivity.this, ShowWishListActivity.class); 
					startActivity(intent);
			    }
			}
        });
        // Insert into basket
        basket.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
			    String userId = sharedPreferences.getString("userId", "");
			    if(userId.equals("0")){
			    	Intent intent = new Intent().setClass(ProductDetailsActivity.this, LoginActivity.class); 
					startActivity(intent);
			    }
			    
			    else{
			    	// Checks if the product already in basket
				    CheckList checkList = new CheckList();
				    if(checkList.checkBasket(ProductDetailsActivity.this, userId.trim(), item)){
						DBAdapter db = new DBAdapter(ProductDetailsActivity.this);
						db.open();
						// Insert into Basket
						db.insertBasket(userId.trim(),item);
						db.close();
				    }
				    // Redirects to basket display
					Intent intent = new Intent().setClass(ProductDetailsActivity.this,ShowBasketActivity.class); 
					startActivity(intent);
			    }
			}
        });
        
        // Redirects to page to enter payment details
        buy.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
			    String userId = sharedPreferences.getString("userId", "");
			    if(userId.equals("0")){
			    	Intent intent = new Intent().setClass(ProductDetailsActivity.this, LoginActivity.class); 
					startActivity(intent);
			    }
			    else{
				Intent intent = new Intent().setClass(getBaseContext(),PaymentDetailsActivity.class );
				intent.putExtra("item",item);
				intent.putExtra("cost",cost);
				startActivity(intent);
			    }
			}
        });
			Bitmap bp;
			bp =BitmapFactory.decodeByteArray(productDetailObj.getImage(),0, productDetailObj.getImage().length);
			im.setImageBitmap(bp);
			cost = Integer.toString(productDetailObj.getPrice());
            name.setText(productDetailObj.getName());
            price.setText(Integer.toString(productDetailObj.getPrice()));
            vendor.setText(productDetailObj.getVendor());
            comments.setText(productDetailObj.getComments());
       
	    // TODO Auto-generated method stub
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
		        				Intent intent = new Intent().setClass(ProductDetailsActivity.this, TabWidgetActivity.class); 
		        				startActivity(intent);
	                            break;
		        
		        case R.id.logoutlog:
					        	Intent homeintent = new Intent().setClass(ProductDetailsActivity.this, LogoActivity.class); 
			    				startActivity(homeintent);
			    				break;
		    }
		    return true;
		}
		
}
