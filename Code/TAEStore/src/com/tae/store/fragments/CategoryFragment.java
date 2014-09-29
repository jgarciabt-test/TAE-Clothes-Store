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
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tae.store.MainActivity;
import com.tae.store.R;
import com.tae.store.adapters.CategoryListAdapter;
import com.tae.store.app.AppController;
import com.tae.store.model.Category;
import com.tae.store.utilities.ServerUrl;

public class CategoryFragment extends SherlockListFragment {

	private String rootCategory;
	
	private ProgressDialog pDialog;	
	private CategoryListAdapter adapter;
	private ArrayList<Category> list;
	private Context context;
	
	public CategoryFragment(){
	}
	
	public CategoryFragment(String rootCategory, Context context ){
		this.context = context;
		this.rootCategory = rootCategory;
		list = new ArrayList<Category>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("CYCLE", "OnCreateView Cat");
		
		ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.category_list, null, false);
		
		if(context == null){
			context = getActivity();
		}
		
		if(savedInstanceState == null){
			Log.v("SAVE", "savedInstanceState is NULL");
			pDialog = new ProgressDialog(context);
			pDialog.setMessage(getResources().getString(R.string.loading));
			pDialog.show();
			makeRequest();
		}else{
			Log.v("SAVE", "savedInstanceState is NOT NULL");
			list = savedInstanceState.getParcelableArrayList("list");
		}
		
		TextView rootCat = (TextView) rootView.findViewById(R.id.txt_category_list_title);
		rootCat.setText(rootCategory);
		adapter = new CategoryListAdapter(getActivity(), list);
		setListAdapter(adapter);
		
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("list", list);
		super.onSaveInstanceState(outState);
		Log.v("SAVE", "onSaveInstanceState Cat");
		
		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String catId = list.get(position).getId();
		String catName = list.get(position).getName();
		MainActivity.replaceFragment(new ProductListFragment(catId,catName,context),"PRODUCT_LIST_FRAGMENT",true);
	}
	
	private void makeRequest(){
		
		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL+ServerUrl.GET_CATEGORIES+rootCategory,
				new Listener<JSONArray>() {
					public void onResponse(JSONArray response) {
						pDialog.hide();
						
							try {
								JSONObject obj = response.getJSONObject(0);
								JSONArray array = (JSONArray) obj.get("categories");
								for(int i=0;i<array.length();i++){
									obj = array.getJSONObject(i);
									Category cat = new Category();
									cat.setId(obj.getString("cat_id"));
									cat.setName(obj.getString("cat_name"));
									cat.setLower_price(obj.getString("cat_lowest_price"));
									cat.setUrl_pic(obj.getString("pic_url"));
									list.add(cat);
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


@Override
	public void onPause() {
	Log.v("CYCLE", "OnPause Cat");
		super.onPause();
		
	}

	@Override
	public void onResume() {
		Log.v("CYCLE", "OnResume Cat");
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.v("CYCLE", "OnStart Cat");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.v("CYCLE", "OnStop Cat");
		adapter.clearAdapter();
		super.onStop();
	}

}
