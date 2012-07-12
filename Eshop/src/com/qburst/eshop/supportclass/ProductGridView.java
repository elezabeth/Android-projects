package com.qburst.eshop.supportclass;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qburst.eshop.pojo.ClassObject;
import com.qburst.eshop.pojo.Product;
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



public class ProductGridView extends BaseAdapter  {
	 
	  private Bitmap[] bitmap;
	  private String[] text ; 
	  private Context context;
	  private LayoutInflater layoutInflater;
	  Product productlist[] = new Product[10];
	  public ProductGridView(Context c){
		  context = c;
		  layoutInflater = LayoutInflater.from(context);
		  ClassObject classObj = ClassObject.getClassObject();
		  List<Product> productList = classObj.getProductList();
		  final int NumberOfItem = productList.size();
		  bitmap = new Bitmap[NumberOfItem];
		  text =new String[NumberOfItem]; 
		  for (int i=0;i<productList.size();i++) {
			  productlist[i] = productList.get(i);
		  }
		  for(int i = 0; i < productList.size(); i++){
			  bitmap[i] = BitmapFactory.decodeByteArray(productlist[i].getImage(),0, productlist[i].getImage().length);
		  }
		  for(int i = 0; i < productList.size(); i++){
			  text[i] = productlist[i].getName();
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
	   // TODO Auto-generated method stub
	    
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
	   textView.setText(text[position]);
	   return grid;
	  }
	 }
