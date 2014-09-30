package com.tae.store.fragments;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import com.tae.store.utilities.ServerUrl;

public class CustomMapFragment extends SherlockFragment implements
		OnInfoWindowClickListener {

	private ViewGroup rootView;
	private GoogleMap googleMap;
	private ImageButton btnToList;
	private ArrayList<Store> list = new ArrayList<Store>();
	private ProgressDialog pDialog;
	private ImageButton btnMyLocation;
	private ImageButton btnSearch;
	private EditText et_search;
	private LocationTracker locationTracker;
	private LatLng currentLocation;
	private LayoutInflater mInflater;
	SupportMapFragment mapFragment;
	private Object savedInstanceState;

	public CustomMapFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mapFragment = SupportMapFragment.newInstance();
		FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.map, mapFragment);
		fragmentTransaction.commit();
		
		mInflater = inflater;
		if (rootView != null) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}

		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_location,
				container, false);

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

		//setUpMap();
		this.savedInstanceState = savedInstanceState;
		

		// To list listener
		btnToList = (ImageButton) rootView.findViewById(R.id.btn_to_list);
		btnToList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.replaceFragment(new StoreListFragment(list),
						"STORE_LIST_FRAGMENT", true);
			}
		});

		// To list listener
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

		et_search
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
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
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				
				if(mapFragment.getMap() != null)
				{
					setUpMap();
					getView().removeOnLayoutChangeListener(this);
				}
				
			}
		});
		
	}

	private void makeRequest() {

		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL
				+ ServerUrl.GET_ALL_STORES, new Listener<JSONArray>() {
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

	public void setMarkers() {

		googleMap.clear();
		googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
		Store store = null;
		Location storeLocation = new Location("Store");
		Location userLocation = null;

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
				// TODO Mi or KM
				list.get(i).setDistance(
						storeLocation.distanceTo(userLocation) / 1000);
			}
			MarkerOptions m = new MarkerOptions()
					.position(
							new LatLng(store.getLatitude(), store
									.getLongitude())).title(store.getName())
					.snippet(store.getAddress() + ", " + store.getCity());

			m.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
			googleMap.addMarker(m);
		}
	}

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
			Toast.makeText(getActivity(), "GPS possition is not available",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void moveCamera(LatLng position) {
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(position).zoom(13).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("list", list);
		super.onSaveInstanceState(outState);
	}

	public class MyInfoWindowAdapter implements InfoWindowAdapter {

		private final View myContentsView;

		public MyInfoWindowAdapter() {
			// TODO possible cause of crash when change orientation
			myContentsView = mInflater.inflate(R.layout.custom_marker, null);
		}

		@Override
		public View getInfoContents(Marker marker) {

			TextView txtName = (TextView) myContentsView
					.findViewById(R.id.txt_store_map_name);
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
				// TODO shared preferences to KM or MI
				float distance = markerLocation.distanceTo(userLocation) / 1000;
				txtDistance.setText(new DecimalFormat("##.##").format(distance)
						+ " Mi");
			} else {
				txtDistance.setText("- mi");
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
				MainActivity.replaceFragment(new StoreFragment(list.get(i)),
						"STORE_FRAGMENT", true);
				return;
			}
		}
	}

}
