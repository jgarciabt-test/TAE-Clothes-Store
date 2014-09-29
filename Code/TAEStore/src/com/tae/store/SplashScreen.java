package com.tae.store;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tae.store.app.AppController;
import com.tae.store.model.Category;
import com.tae.store.utilities.ServerUrl;

public class SplashScreen extends SherlockActivity {

	AlertDialog aDialog;
	private final ArrayList<Category> menCategories = new ArrayList<Category>();
	private final ArrayList<Category> womenCategories = new ArrayList<Category>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		getSupportActionBar().hide();

		 makeRequestCategories();
	}

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
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				i.putParcelableArrayListExtra("men", menCategories);
				i.putParcelableArrayListExtra("women", womenCategories);
				i.putExtra("test", "Test");

				startActivity(i);
				finish();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("VOLLEY_ERROR", "Error: " + error.getMessage());
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SplashScreen.this);
				builder.setMessage(getResources().getString(R.string.error_loading))
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
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
