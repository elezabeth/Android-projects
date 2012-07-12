package com.qburst.eshop.supportclass;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qburst.eshop.pojo.Category;
import com.qburst.eshop.pojo.CategoryObject;

import com.qburst.eshop.product.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryGridView extends BaseAdapter{
	private Context context;
	private LayoutInflater layoutInflater;
	Category categoryObjects[] ;
	String nameList[];
	byte imageList[][];
	 private Bitmap[] bitmap;
	public CategoryGridView(Context c,String response){
		   context = c;
		   layoutInflater = LayoutInflater.from(context);
		   CategoryObject categoryObject = CategoryObject.getCategoryObject();
		   List<Category> categoryList = categoryObject.getCategory();
		   categoryObjects = new Category[categoryList.size()];
		   nameList = new String[categoryList.size()];
		   imageList = new byte[categoryList.size()][categoryList.size()];
		   bitmap = new Bitmap[categoryList.size()];
		   for (int i=0;i<categoryList.size();i++) {
				 categoryObjects[i] = categoryList.get(i);
				 nameList[i] = categoryObjects[i].getName();
				 imageList[i] = categoryObjects[i].getImage();
				 bitmap[i] = BitmapFactory.decodeByteArray(imageList[i], 0, imageList[i].length);
				}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bitmap.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bitmap[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		 return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View grid;
		   if(convertView==null){
		    grid = new View(context);
		    grid = layoutInflater.inflate(R.layout.gridlayout, null);
		   }else{
		    grid = (View)convertView;
		   }
		   ImageView imageView = (ImageView)grid.findViewById(R.id.image);
		   imageView.setImageBitmap(bitmap[position]);
		   TextView textView = (TextView)grid.findViewById(R.id.text);
		   textView.setText(nameList[position]);
		    
		   return grid;
		  }

}
