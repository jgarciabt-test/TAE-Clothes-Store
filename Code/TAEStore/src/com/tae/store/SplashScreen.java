package com.tae.store;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tae.store.app.AppController;
import com.tae.store.model.Category;
import com.tae.store.model.Product;
import com.tae.store.utilities.ServerUrl;

/**
 * Acts like Splash screen. It does server request
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 * */
public class SplashScreen extends RoboActivity {

	/** Dialog used in case that server connection fails */
	AlertDialog aDialog;
	/** Store Category objects passed by the server */
	private final ArrayList<Category> menCategories = new ArrayList<Category>();
	/** Store Category objects passed by the server */
	private final ArrayList<Category> womenCategories = new ArrayList<Category>();
	/** Store Product object passed by the server */
	private final ArrayList<Product> offerProducts = new ArrayList<Product>();
	/**
	 * Intent to start <i>MainActivity</i> with the ArrayLists
	 * <i>menCategories</i>, <i> womenCategories</i> and <i>offerProducts</i>
	 */
	private Intent i;

	@InjectView(R.id.iv_splash)
	private ImageView logo;

	/**
	 * Create the Activity and do the request to the server
	 * 
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		getActionBar().hide();

		logo.setImageDrawable(getResources().getDrawable(R.drawable.app_name_red));
		i = new Intent(SplashScreen.this, MainActivity.class);

		makeRequestCategories();
	}

	/**
	 * Make a request to the server to get all the categories for Men and Women
	 * using volley. If the request fails an AlertDialog will be displayed with
	 * a message and will close the app
	 */
	private void makeRequestCategories() {

		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL
				+ ServerUrl.GET_ALL_CATEGORIES, new Listener<JSONArray>() {
			public void onResponse(JSONArray response) {
				try {
					JSONObject obj = response.getJSONObject(0);
					JSONArray array = (JSONArray) obj.get("categories");
					for (int i = 0; i < array.length(); i++) {
						obj = array.getJSONObject(i);
						Category cat = new Category();
						cat.setId(obj.getString("cat_id"));
						cat.setName(obj.getString("cat_name"));
						cat.setMain_cat(obj.getString("cat_main_cat"));
						cat.setLower_price(obj.getString("cat_lowest_price"));
						cat.setUrl_pic(obj.getString("pic_url"));
						if (cat.getMain_cat().matches("Men")) {
							menCategories.add(cat);
						} else {
							womenCategories.add(cat);
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				makeRequestOffer();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("VOLLEY_ERROR", "Error: " + error.getMessage());
				AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
				builder.setMessage(getResources().getString(R.string.error_loading))
						.setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

		AppController.getInstance().addToRequestQueue(request);
	}

	/**
	 * Make a request to the server to get all the products in offer using
	 * volley. If the request fails an AlertDialog will be displayed with a
	 * message and will close the app
	 */
	private void makeRequestOffer() {

		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL
				+ ServerUrl.GET_OFFER_PRODUCT, new Listener<JSONArray>() {
			public void onResponse(JSONArray response) {
				try {
					JSONObject obj = response.getJSONObject(0);
					JSONArray array = (JSONArray) obj.get("products");
					for (int i = 0; i < array.length(); i++) {
						obj = array.getJSONObject(i);
						Product prod = new Product();
						prod.setId(obj.getString("prod_id"));
						prod.setName(obj.getString("prod_name"));
						prod.setDescription(obj.getString("prod_desc"));
						prod.setDetails(obj.getString("prod_detail"));
						prod.setPrice(obj.getInt("prod_price"));
						if (obj.getString("prod_offer") == "1") {
							prod.setPromoted(true);
						} else {
							prod.setPromoted(false);
						}
						prod.setUrl_pic(obj.getString("pic_url"));
						offerProducts.add(prod);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				i.putParcelableArrayListExtra("men", menCategories);
				i.putParcelableArrayListExtra("women", womenCategories);
				i.putParcelableArrayListExtra("offer", offerProducts);

				startActivity(i);
				finish();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("VOLLEY_ERROR", "Error: " + error.getMessage());
				AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
				builder.setMessage(getResources().getString(R.string.error_loading))
						.setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

		AppController.getInstance().addToRequestQueue(request);
	}
}
