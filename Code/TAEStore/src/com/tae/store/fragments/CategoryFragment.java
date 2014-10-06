package com.tae.store.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
	private ArrayList<Category> list = new ArrayList<Category>();

	public CategoryFragment() {
	}

	public CategoryFragment(String rootCategory, Context context) {
		this.rootCategory = rootCategory;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.category_list, null, false);

//		if (savedInstanceState == null) {
//			if (list.isEmpty()) {
//				pDialog = new ProgressDialog(getActivity());
//				pDialog.setMessage(getResources().getString(R.string.loading));
//				pDialog.show();
//				makeRequest();
//			}
//		} else {
//			list = savedInstanceState.getParcelableArrayList("list");
//		}
		
		if(savedInstanceState!=null){
			list = savedInstanceState.getParcelableArrayList("list");
			rootCategory = savedInstanceState.getString("category");
		}
		if (list.isEmpty()) {
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage(getResources().getString(R.string.loading));
			pDialog.show();
			makeRequest();
		}
		

		TextView rootCat = (TextView) rootView
				.findViewById(R.id.txt_category_list_title);
		rootCat.setText(rootCategory);
		adapter = new CategoryListAdapter(getActivity(), list);
		setListAdapter(adapter);

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("list", list);
		outState.putString("category", rootCategory);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String catId = list.get(position).getId();
		String catName = list.get(position).getName();
		MainActivity.PRODUCT = catId;
		MainActivity.replaceFragment(new ProductListFragment(catId, catName,
				getActivity()), "PRODUCT_LIST_FRAGMENT", true);
	}

	private void makeRequest() {

		rootCategory = MainActivity.ROOT_CATEGORY;
		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL
				+ ServerUrl.GET_CATEGORIES + rootCategory,
				new Listener<JSONArray>() {
					public void onResponse(JSONArray response) {

						try {
							JSONObject obj = response.getJSONObject(0);
							JSONArray array = (JSONArray) obj.get("categories");
							for (int i = 0; i < array.length(); i++) {
								obj = array.getJSONObject(i);
								Category cat = new Category();
								cat.setId(obj.getString("cat_id"));
								cat.setName(obj.getString("cat_name"));
								cat.setLower_price(obj
										.getString("cat_lowest_price"));
								cat.setUrl_pic(obj.getString("pic_url"));
								list.add(cat);
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

						adapter.notifyDataSetChanged();
						pDialog.dismiss();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						pDialog.dismiss();
						VolleyLog.d("VOLLEY_ERROR",
								"Error: " + error.getMessage());
					}
				});

		AppController.getInstance().addToRequestQueue(request);
	}

}
