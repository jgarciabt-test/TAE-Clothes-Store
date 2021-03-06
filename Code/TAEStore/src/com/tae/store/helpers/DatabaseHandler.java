package com.tae.store.helpers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tae.store.model.Product;

/**
 * Database helper where all the product that the user put in the bag are saved.
 * 
* @author      Jose Garcia 
* @version     1.0                 
* @since       2014-10-08         
*/
public class DatabaseHandler extends SQLiteOpenHelper {


	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "USER_BAG";
	private static final String TABLE_PRODUCTS = "products";

	private static final String KEY_ID = "prod_id";
	private static final String KEY_NAME = "prod_name";
	private static final String KEY_DESC = "prod_desc";
	private static final String KEY_PRICE = "prod_price";
	private static final String KEY_SIZE = "prod_size";
	private static final String KEY_MAIN_PIC = "prod_main_pic";
	private static final String KEY_OFFER = "prod_offer";

	private ArrayList<Product> bagList;
	public SQLiteDatabase DB;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS "
				+ TABLE_PRODUCTS + "(" + KEY_ID + " int(11) NOT NULL,"
				+ KEY_NAME + " varchar(50) NOT NULL," + KEY_DESC
				+ " text NOT NULL," + KEY_PRICE + " float NOT NULL," + KEY_SIZE
				+ " varchar(10) NOT NULL," + KEY_OFFER
				+ " tinyint(1) NOT NULL," + KEY_MAIN_PIC
				+ " varchar(50) NOT NULL" + ")";

		db.execSQL(CREATE_PRODUCT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		onCreate(db);
	}

	/**
	 * Insert one product on the database
	 *
	 * @param product Product to insert on the database.
	 * @param size Size of the product that the user want to buy
	 */
	public void addProduct(Product product, String size) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_ID, product.getId());
		values.put(KEY_NAME, product.getName());
		values.put(KEY_DESC, product.getDescription());
		values.put(KEY_PRICE, product.getPrice());
		values.put(KEY_SIZE, size);
		values.put(KEY_MAIN_PIC, product.getUrl_pic());
		values.put(KEY_OFFER, product.getPromoted() ? 1 : 0);

		Log.v("DATABASE", "Inserted: " + product.getUrl_pic());
		db.insert(TABLE_PRODUCTS, null, values);
		db.close();
	}

	/**
	 * Delete one product of the database.
	 * 
	 * @param productId Id of the product to delete.
	 */
	public void deleteProduct(String productId) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PRODUCTS, KEY_ID + " = ?", new String[] { productId });
		db.close();
	}

	/**
	 * Get all the product that the user has on the bag.
	 * @return            ArrayList<Product> with all the product of the database.
	 */
	public ArrayList<Product> getBag() {
		try {
			if (bagList == null) {
				bagList = new ArrayList<Product>();
			}
			bagList.clear();

			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					Product product = new Product();
					product.setId(cursor.getString(0));
					product.setName(cursor.getString(1));
					product.setDescription(cursor.getString(2));
					product.setPrice(Float.parseFloat(cursor.getString(3)));
					product.setSize(cursor.getString(4));
					product.setUrl_pic(cursor.getString(6));
					if (cursor.getInt(5) == 1) {
						product.setPromoted(true);
					} else {
						product.setPromoted(false);
					}

					bagList.add(product);
				} while (cursor.moveToNext());
			}

			cursor.close();
			db.close();
			return bagList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bagList;
	}

	/**
	 * Delete all the content of the database.
	 */
	public void deleteAll(){
	
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_PRODUCTS);	
	}
}
