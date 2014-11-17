package com.tae.store.fragments;

import java.util.HashMap;
import java.util.Map;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tae.store.MainActivity;
import com.tae.store.R;
import com.tae.store.app.AppController;
import com.tae.store.utilities.SPTags;
import com.tae.store.utilities.ServerUrl;

/**
 * Fragment where the suer can change some preferences.
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
public class PreferencesFragment extends Fragment {

	/** Shared preferences */
	private SharedPreferences preferences;
	/** Spinner to select the distance units */
	private Spinner spUnit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.user_preferences, container,
				false);

		NumberPicker np = (NumberPicker) rootView.findViewById(R.id.numberPicker1);
		preferences = getActivity().getPreferences(MainActivity.MODE_PRIVATE);

		if(!preferences.getBoolean(SPTags.ALL_GCM_DATA, false)){
			updateDevice();
		}
		
		
		//Try to put on the spinner the users preference. Default Miles
		spUnit = (Spinner) rootView.findViewById(R.id.sp_units);
		spUnit.setSelection(preferences.getInt(SPTags.UNIT, 0));
		spUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
			// when the user change the selected option in the spinner, this option is saved
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt(SPTags.UNIT, spUnit.getSelectedItemPosition()).commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		// Number picker set up
		np.setMinValue(1);
		np.setMaxValue(100);
		np.setValue(preferences.getInt(SPTags.DISTANCE, 6));
		np.setWrapSelectorWheel(false);

		np.setOnValueChangedListener(new OnValueChangeListener() {
			// when the user change the number picker, the value is saved.
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt(SPTags.DISTANCE, newVal).commit();
			}
		});

		TextView txtUserName = (TextView) rootView.findViewById(R.id.txt_preferences_loged);
		
		txtUserName.setText(preferences.getString(SPTags.NAME, ""));
		
		Button btnLogOut = (Button) rootView.findViewById(R.id.btn_preferences_logout);
		btnLogOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//Clean all the preferences when the user log out.
				SharedPreferences.Editor editor = preferences.edit();

				if (preferences.getBoolean(SPTags.FB_LOGIN, false)) {
					editor.putBoolean(SPTags.FB_LOGIN, false).commit();
					if (LogInFragment.adapter == null) {
						LogInFragment.adapter = new SocialAuthAdapter(new ResponseListener());
						LogInFragment.adapter.authorize(getActivity(), Provider.FACEBOOK);
					}
					LogInFragment.adapter.signOut(getActivity(), Provider.FACEBOOK.toString());
				} else {
					editor.putBoolean(SPTags.TAE_LOGIN, false).commit();
				}
				editor.putBoolean(SPTags.LOGGED, false).commit();
				MyTaeFragment.replaceFragment(new LogInFragment(), "LOGIN_FRAGMENT");

			}
		});

		return rootView;
	}
	
	private void updateDevice(){
		StringRequest postRequest = new StringRequest(Request.Method.POST, ServerUrl.BASE_URL
				+ ServerUrl.UPDATE_DEVICE, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				// response
				Log.v("Response", response);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(SPTags.ALL_GCM_DATA, true);
				Toast.makeText(getActivity(), "Device updated", Toast.LENGTH_SHORT).show();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// error
				Toast.makeText(getActivity(), "There was an error", Toast.LENGTH_SHORT).show();
				Log.d("Error.Response", error.toString());
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();

				String name = preferences.getString(SPTags.NAME, "");
				String email = preferences.getString(SPTags.EMAIL, "");
				String regId = preferences.getString(SPTags.GCM_ID, "");

				Log.v("Response", "name: "+ name);
				Log.v("Response", "email: "+ email);
				Log.v("Response", "ID: "+ regId);
				
				params.put("name", name);
				params.put("email", email);
				params.put("gcm_regid", regId);

				return params;
			}
		};

		AppController.getInstance().addToRequestQueue(postRequest);
	}
	
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO
		super.onSaveInstanceState(outState);
	}

	private final class ResponseListener implements DialogListener {
		public void onComplete(Bundle values) {
			LogInFragment.adapter.getUserProfileAsync(new ProfileDataListener());

		}

		@Override
		public void onBack() {
//			// TODO Auto-generated method stub
//			Toast.makeText(getActivity().getApplicationContext(), "facebook back",
//					Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onCancel() {
//			// TODO Auto-generated method stub
//			Toast.makeText(getActivity().getApplicationContext(), "facebook cancel",
//					Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onError(SocialAuthError arg0) {
//			// TODO Auto-generated method stub
//			Toast.makeText(getActivity().getApplicationContext(), "facebook error",
//					Toast.LENGTH_SHORT).show();

		}

	}

	private final class ProfileDataListener implements SocialAuthListener<Profile> {

		@Override
		public void onError(SocialAuthError arg0) {
		}

		@Override
		public void onExecute(String arg0, Profile arg1) {

		}
	}

}
