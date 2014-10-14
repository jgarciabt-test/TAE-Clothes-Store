package com.tae.store.fragments;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tae.store.MainActivity;
import com.tae.store.R;
import com.tae.store.app.AppController;
import com.tae.store.helpers.LocationTracker;
import com.tae.store.model.Store;
import com.tae.store.utilities.SPTags;
import com.tae.store.utilities.ServerUrl;

/**
 * Map fragment where all the stores are displayed.
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
public class CustomMapFragment extends SherlockFragment implements OnInfoWindowClickListener {

	/** SharedPreferences to get the users preferences */
	public SharedPreferences preferences;
	private ViewGroup rootView;
	private GoogleMap googleMap;
	/** ImageButton to change a List view */
	private ImageButton btnToList;
	/** ArrayList with all the Store objects */
	private ArrayList<Store> list = new ArrayList<Store>();
	/** Progress dialog */
	private ProgressDialog pDialog;
	/** ImageButton to get the device location */
	private ImageButton btnMyLocation;
	/** ImageButton to search a location */
	private ImageButton btnSearch;
	/** EditText to search a location */
	private EditText et_search;
	/** LocationTracker */
	private LocationTracker locationTracker;
	/** Device current location */
	private LatLng currentLocation;
	/** LayoutInflater */
	private LayoutInflater mInflater;
	SupportMapFragment mapFragment;
	private Object savedInstanceState;

	/**
	 * Empty constructor.
	 */
	public CustomMapFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mapFragment = SupportMapFragment.newInstance();
		FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction.add(R.id.map, mapFragment);
		fragmentTransaction.commit();

		// Get the device location
		if (locationTracker == null) {
			locationTracker = new LocationTracker(getActivity());
		}
		locationTracker.getLocation();
		if (locationTracker.canGetLocation()) {
			currentLocation = new LatLng(locationTracker.getLatitude(),
					locationTracker.getLongitude());
		}

		mInflater = inflater;
		if (rootView != null) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}

		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_location, container, false);

		if (savedInstanceState != null) {
			list = savedInstanceState.getParcelableArrayList("list");
		} else {
			if (list.size() == 0) {
				pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage(getResources().getString(R.string.loading));
				pDialog.show();
				makeRequest();
			}
		}

		this.savedInstanceState = savedInstanceState;

		// To list listener
		btnToList = (ImageButton) rootView.findViewById(R.id.btn_to_list);
		btnToList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.replaceFragment(new StoreListFragment(list), "STORE_LIST_FRAGMENT",
						true);
			}
		});

		// Device location listener
		btnMyLocation = (ImageButton) rootView.findViewById(R.id.btn_location);
		btnMyLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getCurrentLocation();
			}
		});

		et_search = (EditText) rootView.findViewById(R.id.et_search);
		btnSearch = (ImageButton) rootView.findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!et_search.getText().toString().matches("")) {
					searchPlace(et_search.getText().toString());
				}
			}
		});

		et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					if (!et_search.getText().toString().matches("")) {
						searchPlace(et_search.getText().toString());
					}
					return true;
				}
				return false;
			}
		});

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		getView().addOnLayoutChangeListener(new OnLayoutChangeListener() {

			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom,
					int oldLeft, int oldTop, int oldRight, int oldBottom) {

				if (mapFragment.getMap() != null) {
					setUpMap();
					getView().removeOnLayoutChangeListener(this);
				}

			}
		});

	}

	/**
	 * Make a server request to get all the store information.
	 */
	private void makeRequest() {
		String URL;
		preferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
		int radius = preferences.getInt(SPTags.DISTANCE, -1);

		if (preferences.getInt(SPTags.UNIT, 0) == 1) {
			radius *= 0.62137;
		}

		if (locationTracker.canGetLocation() && radius != -1) {
			URL = ServerUrl.BASE_URL + ServerUrl.GET_CLOSER_STORES + "lat="
					+ currentLocation.latitude + "&lng=" + currentLocation.longitude + "&radius="
					+ radius;
		} else {
			URL = ServerUrl.BASE_URL + ServerUrl.GET_ALL_STORES;
		}

		Log.v("URL", URL);
		JsonArrayRequest request = new JsonArrayRequest(URL, new Listener<JSONArray>() {
			public void onResponse(JSONArray response) {

				try {
					JSONObject obj = response.getJSONObject(0);
					JSONArray array = (JSONArray) obj.get("stores");
					for (int i = 0; i < array.length(); i++) {
						obj = array.getJSONObject(i);
						Store sto = new Store();
						sto.setId(obj.getString("sto_id"));
						sto.setName(obj.getString("sto_name"));
						sto.setAddress(obj.getString("sto_address"));
						sto.setPostCode(obj.getString("sto_pc"));
						sto.setCity(obj.getString("sto_city"));
						sto.setPhone(obj.getString("sto_phone"));
						sto.setOpeningHours(obj.getString("sto_opening"));
						sto.setLatitude(obj.getDouble("sto_lat"));
						sto.setLongitude(obj.getDouble("sto_lng"));
						if (locationTracker.canGetLocation()) {
							sto.setDistance(obj.getDouble("distance"));
						}
						list.add(sto);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				pDialog.dismiss();
				getCurrentLocation();
				setMarkers();
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

	/**
	 * Set up the map.
	 */
	private void setUpMap() {

		if (googleMap == null) {

			googleMap = mapFragment.getMap();

			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(false);
			googleMap.getUiSettings().setZoomControlsEnabled(false);
			googleMap.getUiSettings().setZoomGesturesEnabled(true);
			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.setOnInfoWindowClickListener(this);

			if (savedInstanceState != null) {
				getCurrentLocation();
				setMarkers();
			}
		}

	}

	/**
	 * Look for a specific place like address, city, post code, etc.
	 * 
	 * @param address
	 *            String with the place that we want to look for.
	 */
	public void searchPlace(String address) {
		Geocoder geoCoder = new Geocoder(getActivity());

		List<Address> addressList;
		try {
			addressList = geoCoder.getFromLocationName(address, 1);
			Address place = addressList.get(0);
			if (place.hasLatitude() && place.hasLongitude()) {
				double selectedLat = place.getLatitude();
				double selectedLng = place.getLongitude();
				moveCamera(new LatLng(selectedLat, selectedLng));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Set all the markers on the map. According to the users preferences the
	 * distance will be displayed as Miles or Kilometres.
	 */
	public void setMarkers() {

		googleMap.clear();
		googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
		Store store = null;
		Location storeLocation = new Location("Store");
		Location userLocation = null;
		preferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);

		// Get the user location
		if (locationTracker.canGetLocation()) {
			userLocation = new Location("User");
			userLocation.setLatitude(currentLocation.latitude);
			userLocation.setLongitude(currentLocation.longitude);
		}

		for (int i = 0; i < list.size(); i++) {
			store = list.get(i);
			if (locationTracker.canGetLocation()) {
				storeLocation.setLatitude(store.getLatitude());
				storeLocation.setLongitude(store.getLongitude());

				// Get the distance
				if (preferences.getInt(SPTags.UNIT, 0) == 1) { // KM
					list.get(i).setDistance(storeLocation.distanceTo(userLocation) / 1000);
				} else { // Mi
					list.get(i).setDistance(storeLocation.distanceTo(userLocation) / 1609);
				}

			}
			MarkerOptions m = new MarkerOptions()
					.position(new LatLng(store.getLatitude(), store.getLongitude()))
					.title(store.getName()).snippet(store.getAddress() + ", " + store.getCity());

			m.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
			googleMap.addMarker(m);
		}
	}

	/**
	 * Store the current device location on <i>currentLocation</i> if it's
	 * possible.
	 */
	private void getCurrentLocation() {
		if (locationTracker == null) {
			locationTracker = new LocationTracker(getActivity());
		}
		locationTracker.getLocation();
		if (locationTracker.canGetLocation()) {
			currentLocation = new LatLng(locationTracker.getLatitude(),
					locationTracker.getLongitude());
			moveCamera(currentLocation);
		} else {
			Toast.makeText(getActivity(), "GPS possition is not available", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * Move the camera to specific location.
	 * 
	 * @param position
	 *            Position where the camera will be moved.
	 */
	private void moveCamera(LatLng position) {
		CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(13)
				.build();

		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("list", list);
		super.onSaveInstanceState(outState);
	}

	/** Class that implements InfoWindowAdapter to customize map markers.
	 * 
	 * @author Jose Garcia
	 * @version 1.0
	 * @since 2014-10-08
	 */
	public class MyInfoWindowAdapter implements InfoWindowAdapter {

		private final View myContentsView;

		public MyInfoWindowAdapter() {
			myContentsView = mInflater.inflate(R.layout.custom_marker, null);
		}

		@Override
		public View getInfoContents(Marker marker) {

			float distance = -1;
			TextView txtName = (TextView) myContentsView.findViewById(R.id.txt_store_map_name);
			TextView txtAddress = (TextView) myContentsView
					.findViewById(R.id.txt_store_map_address);
			TextView txtDistance = (TextView) myContentsView
					.findViewById(R.id.txt_store_map_distance);

			if (locationTracker.canGetLocation()) {
				Location userLocation = new Location("User");
				userLocation.setLatitude(currentLocation.latitude);
				userLocation.setLongitude(currentLocation.longitude);

				Location markerLocation = new Location("Marker");
				markerLocation.setLatitude(marker.getPosition().latitude);
				markerLocation.setLongitude(marker.getPosition().longitude);

				String unit;
				if (preferences.getInt(SPTags.UNIT, 0) == 1) {
					distance = markerLocation.distanceTo(userLocation) / 1000;
					unit = " KM";
				} else {
					distance = markerLocation.distanceTo(userLocation) / 1609;
					unit = " Mi";
				}
				txtDistance.setText(new DecimalFormat("##.##").format(distance) + unit);
			} else {
				if (preferences.getInt(SPTags.UNIT, 0) == 1) {
					txtDistance.setText("- Km");
				} else {
					txtDistance.setText("- Mi");
				}
			}

			txtName.setText(marker.getTitle());
			txtAddress.setText(marker.getSnippet());

			return myContentsView;
		}

		@Override
		public View getInfoWindow(Marker arg0) {
			return null;
		}

	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().matches(arg0.getTitle())) {
				MainActivity
						.replaceFragment(new StoreFragment(list.get(i)), "STORE_FRAGMENT", true);
				return;
			}
		}
	}

}
