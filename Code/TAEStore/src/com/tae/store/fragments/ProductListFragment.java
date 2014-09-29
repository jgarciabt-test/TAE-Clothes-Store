package com.tae.store.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tae.store.R;
import com.tae.store.adapters.ProductListAdapter;
import com.tae.store.app.AppController;
import com.tae.store.model.Category;
import com.tae.store.model.Product;
import com.tae.store.utilities.ServerUrl;

public class ProductListFragment extends SherlockFragment {

	private ArrayList<Product> list;
	private String categoryId;
	private String categoryTitle;
	private ProgressDialog pDialog;
	private Context context;
	private ProductListAdapter adapter;

	public ProductListFragment(){
	}
	
	public ProductListFragment(String categoryId,String categoryTitle, Context context){
		this.context = context;
		this.categoryId = categoryId;
		this.categoryTitle = categoryTitle;
		list = new ArrayList<Product>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.product_list, null, false);

		if (savedInstanceState == null
				|| !savedInstanceState.containsKey("list")) {
			pDialog = new ProgressDialog(context);
			pDialog.setMessage(getResources().getString(R.string.loading));
			makeRequest();
		} else {
			context = getActivity().getApplicationContext();
			list = savedInstanceState.getParcelableArrayList("list");
		}

		TextView txtCategory = (TextView)rootView.findViewById(R.id.txt_product_list_title);
		txtCategory.setText(categoryTitle);
		adapter = new ProductListAdapter(getActivity(),android.R.layout.simple_list_item_1, list);
		GridView grid=(GridView) rootView.findViewById(R.id.grid);
        grid.setAdapter(adapter);
		return rootView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.v("SAVE", "onSaveInstanceState");
		outState.putParcelableArrayList("list", list);
	}

@Override
	public void onPause() {
	Log.v("CYCLE", "OnPause");
		super.onPause();
		
	}

	@Override
	public void onResume() {
		Log.v("CYCLE", "OnResume");
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.v("CYCLE", "OnStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.v("CYCLE", "OnStore");
		super.onStop();
	}

private void makeRequest(){
		
		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL+ServerUrl.GET_ALL_PRODUCTS+categoryId,
				new Listener<JSONArray>() {
					public void onResponse(JSONArray response) {
						pDialog.hide();
						
							try {
								JSONObject obj = response.getJSONObject(0);
								JSONArray array = (JSONArray) obj.get("products");
								for(int i=0;i<array.length();i++){
									obj = array.getJSONObject(i);
									Product prod = new Product();
									prod.setId(obj.getString("prod_id"));
									prod.setName(obj.getString("prod_name"));
									prod.setDescription(obj.getString("prod_desc"));
									prod.setDetails(obj.getString("prod_detail"));
									prod.setPrice(obj.getInt("prod_price"));
									if(obj.getString("prod_offer") == "1"){
										prod.setPromoted(true);
									} else{
										prod.setPromoted(false);
									}
									prod.setUrl_pic(obj.getString("pic_url"));
									list.add(prod);
								}
								
							} catch (JSONException e) {
								e.printStackTrace();
							}
							
							adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						pDialog.hide();
						VolleyLog.d("VOLLEY_ERROR", "Error: " + error.getMessage());
					}
				});

		AppController.getInstance().addToRequestQueue(request);
	}
	
}
