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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tae.store.MainActivity;
import com.tae.store.R;
import com.tae.store.adapters.ProductListAdapter;
import com.tae.store.app.AppController;
import com.tae.store.model.Product;
import com.tae.store.utilities.ServerUrl;

/**
 * Fragment where all the product of a category will be displayed.
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
public class ProductListFragment extends SherlockFragment {

	/** ArrayList with all the Product objects */
	private ArrayList<Product> list = new ArrayList<Product>();
	/** String with the category ID of the product */
	private String categoryId;
	/** String with the category name */
	private String categoryTitle;
	/** Progress dialog */
	private ProgressDialog pDialog;
	/** ProductListAdapter */
	private ProductListAdapter adapter;

	/**
	 * Empty constructor.
	 */
	public ProductListFragment() {
	}

	/**
	 * Constructor.
	 * 
	 * @param categoryId Category ID .
	 * @param categoryTitle Category title. 
	 * @param context Application context.
	 */
	public ProductListFragment(String categoryId, String categoryTitle, Context context) {
		this.categoryId = categoryId;
		this.categoryTitle = categoryTitle;
		list = new ArrayList<Product>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.product_list, null, false);

		if (savedInstanceState != null) {
			list = savedInstanceState.getParcelableArrayList("list");
			categoryTitle = savedInstanceState.getString("categoryTitle");
			categoryId = savedInstanceState.getString("categoryId");
		}

		if (list.size() == 0) {
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage(getResources().getString(R.string.loading));
			pDialog.show();
			makeRequest();
		}

		TextView txtCategory = (TextView) rootView.findViewById(R.id.txt_product_list_title);
		txtCategory.setText(categoryTitle);
		adapter = new ProductListAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
		GridView grid = (GridView) rootView.findViewById(R.id.grid);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnItemClickListener() {
			//Start new fragment when the user select one product
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				MainActivity.replaceFragment(new ItemFragment(list.get(position)),
						"ITEM_DETAIL_FRAGMENT", true);
			}
		});

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("list", list);
		outState.putString("categoryTitle", categoryTitle);
		outState.putSerializable("categoryId", categoryId);
	}

	/**
	 * Make a server request to get all the product for one category.
	 */
	private void makeRequest() {

		if (categoryId == null) {
			categoryId = MainActivity.CATEGORY;
		}

		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL
				+ ServerUrl.GET_ALL_PRODUCTS + categoryId, new Listener<JSONArray>() {
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
						prod.setPrice(Float.parseFloat(obj.getString("prod_price")));
						if (obj.getString("prod_offer") == "1") {
							prod.setPromoted(true);
						} else {
							prod.setPromoted(false);
						}
						prod.setUrl_pic(obj.getString("pic_url"));
						list.add(prod);
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
				VolleyLog.d("VOLLEY_ERROR", "Error: " + error.getMessage());
			}
		});

		AppController.getInstance().addToRequestQueue(request);
	}

}
