package com.tae.store.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;
import com.tae.store.R;
import com.tae.store.app.AppController;
import com.tae.store.model.Product;
import com.tae.store.utilities.ServerUrl;

public class ItemFragment extends SherlockFragment {

	private Product product;
	private ArrayList<String> urlArray;
	private ImageView mainPicture;
	private LinearLayout mCarouselContainer;
	private float initial_items = 4.5f;
	private ProgressDialog pDialog;

	public ItemFragment() {

	}

	public ItemFragment(Product product) {
		this.product = product;
		urlArray = new ArrayList<String>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_item_details, null, false);

		mCarouselContainer = (LinearLayout) rootView
				.findViewById(R.id.carousel);

		if(savedInstanceState == null){
			if(urlArray.isEmpty()){
				pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage(getResources().getString(R.string.loading));
				pDialog.show();

				makeRequest();
			}
		} else{
			product = savedInstanceState.getParcelable("product");
			urlArray = savedInstanceState.getStringArrayList("urlArray");
			setCarousel();
		}

		mainPicture = (ImageView) rootView
				.findViewById(R.id.iv_item_detail_page);
		Spinner spSize = (Spinner) rootView.findViewById(R.id.sp_size);
		

		Display display = ((WindowManager) getActivity().getSystemService(
				getActivity().WINDOW_SERVICE)).getDefaultDisplay();

		int orientation = display.getRotation();

		if (orientation == Surface.ROTATION_90
				|| orientation == Surface.ROTATION_270) {
			initial_items = 7.5f;
		} else {
			initial_items = 4.5f;
		}

		Picasso.with(getActivity().getApplicationContext())
				.load(ServerUrl.BASE_URL + ServerUrl.IMG + product.getUrl_pic())
				.placeholder(R.drawable.ic_launcher).into(mainPicture);
		return rootView;
	}
	
	
	private void addProduct(){
		SharedPreferences sharedPreferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
		//sharedPreferences.edit().p
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelable("product", product);
		outState.putStringArrayList("urlArray", urlArray);	
	}

	public void setCarousel() {

		// Compute the width of a carousel item based on the screen width and
		// number of initial items.
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		final int imageWidth = (int) (displayMetrics.widthPixels / initial_items);

		// Populate the carousel with items
		ImageView imageItem;
		for (int i = 0; i < urlArray.size(); ++i) {
			// Create new ImageView
			imageItem = new ImageView(getActivity());

			// Set the shadow background
			// imageItem.setBackgroundResource(R.drawable.shadow);

			Picasso.with(getActivity())
					.load(ServerUrl.BASE_URL + ServerUrl.IMG + urlArray.get(i))
					.into(imageItem);

			// Set the size of the image view to the previously computed value
			imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth,
					imageWidth));
			imageItem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.v("CAROUSEL", "Pressed");
					mainPicture.setImageDrawable(((ImageView) v).getDrawable());

				}
			});
			// / Add image view to the carousel container
			mCarouselContainer.addView(imageItem);
		}

	}

	private void makeRequest() {

		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL
				+ ServerUrl.GET_ALL_PIC_PROD + product.getId(),
				new Listener<JSONArray>() {
					public void onResponse(JSONArray response) {

						try {
							JSONObject obj = response.getJSONObject(0);
							JSONArray array = (JSONArray) obj.get("pictures");
							for (int i = 0; i < array.length(); i++) {
								obj = array.getJSONObject(i);
								urlArray.add(obj.getString("pic_url"));
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}

						setCarousel();
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
