package com.qburst.eshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {
	public static final String Id = "id";
	public static final String UserId = "userid";
	public static final String ProductId = "productid";
	public static final String Date = "date";
	public static final String PurchaseId = "purchaseid";

	private static final String DATABASE_NAME = "test2";
	private static final String DATABASE_TABLE_WISHLIST = "wishlist";
	private static final String DATABASE_TABLE_BASKET = "basket";
	private static final String DATABASE_TABLE_PURCHASE = "purchase";

	
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE_WISHLIST = "create table wishlist (id integer primary key autoincrement, "+UserId+" text not null,"+ProductId+" text not null);";
	private static final String DATABASE_CREATE_BASKET = "create table basket (id integer primary key autoincrement, "+UserId+" text not null,"+ProductId+" text not null);";
	private static final String DATABASE_CREATE_PURCHASE = "create table purchase (id integer primary key autoincrement, "+UserId+" text not null,"+ProductId+" text not null,"+Date+" text not null,"+PurchaseId+" text not null);";

	public static final String TAG = null;

	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
	this.context = ctx;
	DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
	DatabaseHelper(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	db.execSQL(DATABASE_CREATE_WISHLIST);
	db.execSQL(DATABASE_CREATE_BASKET);
	db.execSQL(DATABASE_CREATE_PURCHASE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	+ newVersion + ", which will destroy all old data");
	db.execSQL("DROP TABLE IF EXISTS titles");
	onCreate(db);
	}
	}

	// ---opens the database---
	public DBAdapter open() throws SQLException {
	db = DBHelper.getWritableDatabase();
	return this;
	}

	// ---insert data into the wishlist database---
	public long insertWishlist(String userId, String productId) {
	ContentValues initialValues = new ContentValues();
	initialValues.put(UserId, userId);
	initialValues.put(ProductId,productId);
	
	// initialValues.put(KEY_PUBLISHER, publisher);
	return db.insert(DATABASE_TABLE_WISHLIST, null, initialValues);
	}
	
	public void deleteWishlist(String productId){
		db.delete(DATABASE_TABLE_WISHLIST, ProductId
                + " = " + productId, null);
	}

	// ---insert data into the Basket database---
		public long insertBasket(String userId, String productId) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(UserId, userId);
		initialValues.put(ProductId,productId);
		
		// initialValues.put(KEY_PUBLISHER, publisher);
		return db.insert(DATABASE_TABLE_BASKET, null, initialValues);
		}
	
		// ---insert data into the Basket database---
				public long insertPurchase(String userId, String productId,String date,String purchaseid) {
				ContentValues initialValues = new ContentValues();
				initialValues.put(UserId, userId);
				initialValues.put(ProductId,productId);
				initialValues.put(Date,date);
				initialValues.put(PurchaseId,purchaseid);
				// initialValues.put(KEY_PUBLISHER, publisher);
				return db.insert(DATABASE_TABLE_PURCHASE, null, initialValues);
				}
		
	// ---closes the database---
	public void close() {
	DBHelper.close();
	}
	
	public Cursor fetchWishlist(String id) {
//		return db.query(DATABASE_TABLE, new String[] { "userid", "productid" },
//                null, null, null, null, null);
		return db.query(DATABASE_TABLE_WISHLIST, new String[] { "userid", "productid" },"userid like ?",new String[] {id},
                null, null, null, null);

	}
	
	public Cursor fetchBasket(String id) {
		return db.query(DATABASE_TABLE_BASKET, new String[] { "userid", "productid" },"userid like ?",new String[] {id},
                null, null, null, null);
		
	}
	
	public Cursor fetchPurchase(String date) {
//		return db.query(DATABASE_TABLE_PURCHASE, new String[] { "userid", "productid","date" },
//      null, null, null, null,null);
		return db.query(DATABASE_TABLE_PURCHASE, new String[] { "userid", "productid","date","purchaseid" },"date like ?",new String[] {date},
                null, null, null, null);
	}
	public Cursor fetchUserPurchase(String userid) {
//		return db.query(DATABASE_TABLE_PURCHASE, new String[] { "userid", "productid","date" },
//      null, null, null, null,null);
		return db.query(DATABASE_TABLE_PURCHASE, new String[] { "userid", "productid","date","purchaseid" },"userid like ?",new String[] {userid},
                null, null, null, null);
	}
	}
