package com.tae.store.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;
import com.tae.store.R;
import com.tae.store.app.AppController;
import com.tae.store.helpers.DatabaseHandler;
import com.tae.store.model.Product;
import com.tae.store.utilities.ServerUrl;

public class ItemFragment extends SherlockFragment {

	/** Product that the will be displayed on this fragment */
	private Product product;
	/** ArrayList with the URL (String) of the images of the product */
	private ArrayList<String> urlArray;
	/** ArrayList with all the Sizes (String) that the user can choose for this product */
	private ArrayList<String> sizeArray;
	/** ArrayAdapter for the spinner  */
	private ArrayAdapter<String> sizeAdapter;
	/** ImageView where the main picture will be placed*/
	private ImageView mainPicture;
	/** Spinner where the user will select the size in case of add to the bag */
	private Spinner spSize;
	
	/** LinearLayout where all the images will be displayed */
	private LinearLayout mCarouselContainer;
	
	/** Float */
	private float initial_items = 4.5f;
	private ProgressDialog pDialog;

	private DatabaseHandler BAG;

	public ItemFragment() {

	}

	/** Description of myMethod(int a, String b)
	 * 
	 * @param a			Description of a
	 * @param b			Description of b
	 * @return			Description of c
	 */
	public ItemFragment(Product product) {
		this.product = product;
		urlArray = new ArrayList<String>();
		sizeArray = new ArrayList<String>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_details, null,
				false);

		mCarouselContainer = (LinearLayout) rootView.findViewById(R.id.carousel);
		mainPicture = (ImageView) rootView.findViewById(R.id.iv_item_detail_page);
		spSize = (Spinner) rootView.findViewById(R.id.sp_size);

		Display display = ((WindowManager) getActivity().getSystemService(
				getActivity().WINDOW_SERVICE)).getDefaultDisplay();
		int orientation = display.getRotation();
		if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270) {
			initial_items = 9.5f;
		} else {
			initial_items = 4.5f;
		}

		if (savedInstanceState == null) {
			if (urlArray.isEmpty()) {
				pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage(getResources().getString(R.string.loading));
				pDialog.show();

				makeRequestPic();
			}
			if (sizeArray.isEmpty()) {
				makeRequestSize();
			}
		} else {
			product = savedInstanceState.getParcelable("product");
			urlArray = savedInstanceState.getStringArrayList("urlArray");
			sizeArray = savedInstanceState.getStringArrayList("sizeArray");
			setCarousel();
		}

		sizeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				sizeArray);
		spSize.setAdapter(sizeAdapter);

		Button btnAdd = (Button) rootView.findViewById(R.id.btn_add);
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addProduct();
			}
		});

		Picasso.with(getActivity().getApplicationContext())
				.load(ServerUrl.BASE_URL + ServerUrl.IMG + product.getUrl_pic())
				.placeholder(getActivity().getResources().getDrawable(R.drawable.back))
				.into(mainPicture);
		return rootView;
	}

	private void addProduct() {
		if (BAG == null) {
			BAG = new DatabaseHandler(getActivity());
		}

		BAG.addProduct(product, spSize.getSelectedItem().toString());
		Toast.makeText(getActivity(),
				product.getName() + " " + getResources().getString(R.string.msg_added),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putParcelable("product", product);
		outState.putStringArrayList("urlArray", urlArray);
		outState.putStringArrayList("sizeArray", sizeArray);
	}

	public void setCarousel() {

		// Compute the width of a carousel item based on the screen width and
		// number of initial items.
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		final int imageWidth = (int) (displayMetrics.widthPixels / initial_items);

		// Populate the carousel with items
		ImageView imageItem;
		for (int i = 0; i < urlArray.size(); ++i) {
			// Create new ImageView
			imageItem = new ImageView(getActivity());

			// Set the shadow background
			// imageItem.setBackgroundResource(R.drawable.shadow);

			Picasso.with(getActivity()).load(ServerUrl.BASE_URL + ServerUrl.IMG + urlArray.get(i))
					.into(imageItem);

			// Set the size of the image view to the previously computed value
			imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageWidth));
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
		// Just to solve a bug
		Picasso.with(getActivity()).load(ServerUrl.BASE_URL + ServerUrl.IMG + urlArray.get(0))
				.into(mainPicture);

	}

	private void makeRequestPic() {

		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL
				+ ServerUrl.GET_ALL_PIC_PROD + product.getId(), new Listener<JSONArray>() {
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
				VolleyLog.d("VOLLEY_ERROR", "Error: " + error.getMessage());
			}
		});

		AppController.getInstance().addToRequestQueue(request);
	}

	private void makeRequestSize() {

		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL
				+ ServerUrl.GET_ALL_SIZE_PROD + product.getId(), new Listener<JSONArray>() {
			public void onResponse(JSONArray response) {

				try {
					JSONObject obj = response.getJSONObject(0);
					JSONArray array = (JSONArray) obj.get("sizes");
					for (int i = 0; i < array.length(); i++) {
						obj = array.getJSONObject(i);
						sizeArray.add(obj.getString("size_name"));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				sizeAdapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("VOLLEY_ERROR", "Error: " + error.getMessage());
			}
		});

		AppController.getInstance().addToRequestQueue(request);
	}

}
