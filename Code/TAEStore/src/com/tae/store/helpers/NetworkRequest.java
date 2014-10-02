package com.tae.store.helpers;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tae.store.R;
import com.tae.store.app.AppController;

//Implements Singleton
public class NetworkRequest {

	private static NetworkRequest instance = null;
	private ProgressDialog pDialog;
	private Context context;
	private JSONArray networkResponse;

	protected NetworkRequest(Context context) {
		this.context = context;
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(context.getResources().getString(R.string.loading));
	}

	public static NetworkRequest getInstance(Context context) {
		if (instance == null) {
			instance = new NetworkRequest(context);
		}
		return instance;
	}

	public JSONArray makeRequest(String url) {

		pDialog.show();
		networkResponse = new JSONArray();	
		
		JsonArrayRequest request = new JsonArrayRequest(url,
				new Listener<JSONArray>() {
					public void onResponse(JSONArray response) {
						pDialog.hide();
						networkResponse = response;
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						pDialog.hide();
						VolleyLog.d("VOLLEY_ERROR", "Error: " + error.getMessage());
					}
				});

		AppController.getInstance().addToRequestQueue(request);
		return networkResponse;
	}

}