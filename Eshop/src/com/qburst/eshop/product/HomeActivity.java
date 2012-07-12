package com.qburst.eshop.product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qburst.eshop.pojo.Category;
import com.qburst.eshop.pojo.CategoryObject;
import com.qburst.eshop.supportclass.CategoryGridView;
import com.qburst.eshop.supportclass.CustomHttpClient;

public class HomeActivity extends Activity implements
AdapterView.OnItemClickListener {
	 String res;
	 GridView gridView;
	 ProgressDialog mProgress;
	 String response = null;
	/** Displays the category and picks sends the  category selected by user to servlet */
	@Override
	public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
      
       setContentView(R.layout.productlist);
       progressDialog();
       
	   	 }
	// Displays products of a particular category on click
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		Intent intent = new Intent().setClass(this, ProductListActivity.class); 
		intent.putExtra("response",res);
		intent.putExtra("position",position);
		startActivity(intent);
		
	}
	// To display a dialog while loading database
	private void progressDialog() {
		mProgress = new ProgressDialog(this);
		mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgress.setIcon(R.drawable.wait);
		mProgress.setTitle("Loading home page!");
		mProgress.setMessage("Please wait...");
		mProgress.show();
		ProgressThread thread = new ProgressThread();
		Void params = null;
		thread.execute(params);
		}

	// Retrieves data while showing the progress dialog
	private class ProgressThread extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
		
		getCategory();
		return null;

		}

		/*
		* Displays the products under a category in a grid view
		* 
		* 
		*/
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mProgress.dismiss();
			
			gridView = (GridView)findViewById(R.id.product_grid);
	        CategoryGridView adapter = new CategoryGridView(HomeActivity.this,res);
	        gridView.setAdapter(adapter);
	        gridView.setOnItemClickListener(HomeActivity.this);
			
		}
		}
	// Get the products list from database
	public void getCategory(){
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		       postParameters.add(new BasicNameValuePair("category", "book"));
		       
		         try {
		             response = CustomHttpClient.executeHttpPost("http://10.4.0.112:8080/EshopWebServices/GetCategory", postParameters);
		             res=response.toString().trim();
		             
		         } catch (Exception e) {
		         }
		         
		         
		        	 
		        
		         
		         // Parse the string to list of category object
		         Type type = new TypeToken<List<Category>>(){}.getType();
				 List<Category> categoryList = new Gson().fromJson(res, type);
				 CategoryObject categoryObject = CategoryObject.getCategoryObject();
				 categoryObject.setCategory(categoryList);
		         
		}
		
}