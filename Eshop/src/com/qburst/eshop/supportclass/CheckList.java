package com.qburst.eshop.supportclass;

import com.qburst.eshop.database.DBAdapter;

import android.content.Context;
import android.database.Cursor;

public class CheckList {
Context context;
	public boolean checkWishlist(Context context, String userId, String productId){
		this.context = context;
		int flag = 0;
		DBAdapter dBadapter = new DBAdapter(this.context);
		dBadapter.open();
		Cursor c = dBadapter.fetchWishlist(userId);
		if (c == null) {
			// do nothing
		} 
		else {
			if (c.getCount() > 0) {

				if (c.moveToFirst()) {
					do {
						String id = c.getString(1);
						
						if(id.equals(productId)){
							flag = 1;
							System.out.println("pid"+id+" p id"+productId);
						}
					}while (c.moveToNext());
				}
			}
		}
		dBadapter.close();
		if(flag == 1){
			return false;
		}
		else
		return true;
	}
	public boolean checkBasket(Context context, String userId, String productId){
		
		this.context = context;
		int flag = 0;
		DBAdapter dBadapter = new DBAdapter(this.context);
		dBadapter.open();
		Cursor c = dBadapter.fetchBasket(userId);
		if (c == null) {
			// do nothing
		} 
		else {
			if (c.getCount() > 0) {

				if (c.moveToFirst()) {
					do {
						String id = c.getString(1);
						
						if(id.equals(productId)){
							flag = 1;
							System.out.println("pid"+id+" p id"+productId);
						}
					}while (c.moveToNext());
				}
			}
		}
		dBadapter.close();
		if(flag == 1){
			return false;
		}
		else
		return true;
	}
}
