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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qburst.eshop.pojo.Category;
import com.qburst.eshop.pojo.CategoryObject;
import com.qburst.eshop.pojo.ClassObject;
import com.qburst.eshop.pojo.Product;
import com.qburst.eshop.supportclass.CategoryGridView;
import com.qburst.eshop.supportclass.CustomHttpClient;
import com.qburst.eshop.supportclass.ProductGridView;

public class ProductListActivity extends Activity implements
AdapterView.OnItemClickListener{
	public static final String PREFS_NAME = "UserFile";
	GridView gridView;
	String items[] = new String[1];
	String productid[] = new String[1];
	Product productlist[] = new Product[10];
	String productResponse;
	List<Category> categoryList;
	List<Product> productList;
	String userId;
	ProgressDialog mProgress;
	String category;
	/** Gets the list of products in a category */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
	    userId = sharedPreferences.getString("userId", "");
	    setContentView(R.layout.productlist);
	    Bundle responses = getIntent().getExtras();
	    String res = responses.getString("response");
	    int position = responses.getInt("position");
	    // Get the choose category  object 
	    CategoryObject categoryObject = CategoryObject.getCategoryObject();
		List<Category> categoryList = categoryObject.getCategory();
		category = categoryList.get(position).getName();
		progressDialog();
		
		
	}
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		 Intent intent = new Intent().setClass(ProductListActivity.this, ProductDetailsActivity.class); 
		 ClassObject classObj = ClassObject.getClassObject();
		 classObj.setProduct(productList.get(position));
         startActivity(intent);
}
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
		        				Intent intent = new Intent().setClass(ProductListActivity.this, TabWidgetActivity.class); 
		        				startActivity(intent);
	                            break;
		        
		        case R.id.logoutlog:
					        	Intent homeintent = new Intent().setClass(ProductListActivity.this, LogoActivity.class); 
			    				startActivity(homeintent);
			    				break;
		    }
		    return true;
		}
		private void progressDialog() {
			mProgress = new ProgressDialog(this);
			mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgress.setIcon(R.drawable.wait);
			mProgress.setTitle("Loading products!");
			mProgress.setMessage("Please wait...");
			mProgress.show();
			ProgressThread thread = new ProgressThread();
			Void params = null;
			thread.execute(params);
			}
		private class ProgressThread extends AsyncTask<Void, Void, Void> {

			@Override
			protected Void doInBackground(Void... params) {
			getProductList();
			return null;

			}

			/*
			* 
			* Gets the product details from web server  
			* 
			*/
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			mProgress.dismiss();
			// Inflates the grid with product details
			gridView = (GridView)findViewById(R.id.product_grid);
			ProductGridView adapter = new ProductGridView(ProductListActivity.this);
	        gridView.setAdapter(adapter);
	        gridView.setOnItemClickListener(ProductListActivity.this);
	        
			}
			}
			public void getProductList(){
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				// Gets the product in  particular category from web server 
			    postParameters.add(new BasicNameValuePair("category", category));
				try {
					productResponse = CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/GetProductList", postParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Parse the response string to list of product object 
				Type productType = new TypeToken<List<Product>>(){}.getType();
		        String s =productResponse.toString();
				productList = new Gson().fromJson(s,productType);
				ClassObject classObj = ClassObject.getClassObject();
				classObj.setProductList(productList);
			}
			
}
