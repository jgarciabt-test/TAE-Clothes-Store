package com.tae.store.fragments;

import java.util.HashMap;
import java.util.Map;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.tae.store.MainActivity;
import com.tae.store.R;
import com.tae.store.app.AppController;
import com.tae.store.model.Category;
import com.tae.store.utilities.ServerUrl;

public class LogInFragment extends SherlockFragment {

	SocialAuthAdapter adapter;

	private EditText etName;
	private EditText etLastName;
	private EditText etUserName;
	private EditText etEmail;
	private RadioGroup rgGender;
	private Spinner spCountry;
	private EditText etAddress;
	private EditText etPostCode;
	private EditText etCity;
	private EditText etPass;
	private CheckBox terms;
	private Button btnSubmit;

	private EditText editTextUserName;
	private EditText editTextPassword;
	private Dialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_login, container, false);

		Button btnFacebook = (Button) rootView
				.findViewById(R.id.connectWithFbButton);

		btnFacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adapter = new SocialAuthAdapter(new ResponseListener());
				adapter.authorize(getActivity(), Provider.FACEBOOK);

			}
		});

		// Button btnFacebookLogout = (Button) rootView
		// .findViewById(R.id.disconnectWithFbButton);
		//
		// btnFacebookLogout.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// adapter.signOut(getActivity().getApplicationContext(),
		// Provider.FACEBOOK.toString());
		//
		// }
		// });

		etName = (EditText) rootView.findViewById(R.id.registration_name);
		etLastName = (EditText) rootView
				.findViewById(R.id.registration_last_name);
		etUserName = (EditText) rootView
				.findViewById(R.id.registration_user_name);
		rgGender = (RadioGroup) rootView.findViewById(R.id.registration_gender);
		spCountry = (Spinner) rootView.findViewById(R.id.registration_country);
		etAddress = (EditText) rootView.findViewById(R.id.registration_address);
		etPostCode = (EditText) rootView.findViewById(R.id.registration_pc);
		etEmail = (EditText) rootView.findViewById(R.id.registration_email);
		etCity = (EditText) rootView.findViewById(R.id.registration_city);
		etPass = (EditText) rootView.findViewById(R.id.registration_pass);
		terms = (CheckBox) rootView.findViewById(R.id.registration_terms);
		btnSubmit = (Button) rootView.findViewById(R.id.registration_submit);

		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkFields()) {
					makeRequestRegister();
				}
			}
		});

		Button btnTae = (Button) rootView.findViewById(R.id.btn_tae_login);
		btnTae.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				singIn();
			}
		});
		return rootView;
	}

	private void singIn() {
		dialog = new Dialog(getActivity());

		dialog.setContentView(R.layout.login_dialog);
		dialog.setTitle(getActivity().getResources().getString(R.string.log_in));

		// get the Refferences of views
		editTextUserName = (EditText) dialog
				.findViewById(R.id.editTextUserNameToLogin);
		editTextPassword = (EditText) dialog
				.findViewById(R.id.editTextPasswordToLogin);

		Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignIn);

		// Set On ClickListener
		btnSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String userName = editTextUserName.getText().toString();
				makeRequestPassword(userName);
			}
		});

		dialog.show();
	}

	private boolean checkFields() {
		boolean complete = true;

		// Name
		if (etName.getText().toString().matches("")) {
			complete = false;
			etName.setError("Required");
		}
		// Last Name
		if (etLastName.getText().toString().matches("")) {
			complete = false;
			etLastName.setError("Required");
		}

		// User Name
		if (etUserName.getText().toString().matches("")) {
			complete = false;
			etUserName.setError("Required");
		}
		// Pass
		if (etPass.getText().toString().matches("")) {
			complete = false;
			etPass.setError("Required");
		}
		// Email
		if (etEmail.getText().toString().matches("")) {
			complete = false;
			etEmail.setError("Required");
		}

		// Radio Group
		int index = rgGender.getCheckedRadioButtonId();
		if (index == -1) {
			complete = false;
			Toast.makeText(getActivity(), "Please select 'Gender'...",
					Toast.LENGTH_LONG).show();
		}

		// Address
		if (etAddress.getText().toString().matches("")) {
			complete = false;
			etAddress.setError("Required");
		}

		// Post Code
		if (etPostCode.getText().toString().matches("")) {
			complete = false;
			etPostCode.setError("Required");
		}
		// City
		if (etCity.getText().toString().matches("")) {
			complete = false;
			etCity.setError("Required");
		}

		if (!terms.isChecked()) {
			complete = false;
			Toast.makeText(getActivity(),
					"You need to accept the Terms and...", Toast.LENGTH_LONG)
					.show();
		}

		return complete;
	}

	private void makeRequestPassword(String username) {

		JsonArrayRequest request = new JsonArrayRequest(ServerUrl.BASE_URL
				+ ServerUrl.LOGIN + username, new Listener<JSONArray>() {
			public void onResponse(JSONArray response) {

				try {
					JSONObject obj = response.getJSONObject(0);
					JSONArray array = (JSONArray) obj.get("password");
					obj = array.getJSONObject(0);
					String storedPassword = obj.getString("password");

					String password = editTextPassword.getText().toString();
					if (password.equals(storedPassword)) {
						Toast.makeText(getActivity().getApplicationContext(),
								"Login Successfull", Toast.LENGTH_LONG).show();
						dialog.dismiss();
					} else {

						Toast.makeText(getActivity().getApplicationContext(),
								getActivity().getResources().getString(
										R.string.user_pass_no_match),
								Toast.LENGTH_LONG).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getActivity().getApplicationContext(),
							getActivity().getResources().getString(
									R.string.user_name_not_register),
							Toast.LENGTH_LONG).show();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				dialog.dismiss();
				Toast.makeText(
						getActivity().getApplicationContext(),
						getActivity().getResources().getString(
								R.string.server_error), Toast.LENGTH_LONG)
						.show();
				VolleyLog.d("VOLLEY_ERROR", "Error: " + error.getMessage());
			}
		});

		AppController.getInstance().addToRequestQueue(request);
	}

	private void makeRequestRegister() {

		StringRequest postRequest = new StringRequest(Request.Method.POST,
				ServerUrl.BASE_URL + ServerUrl.REGISTER_USER,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// response
						Log.v("Response", response);
						Toast.makeText(getActivity(), "User registered",
								Toast.LENGTH_SHORT).show();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// error
						Toast.makeText(getActivity(), "There was an error",
								Toast.LENGTH_SHORT).show();
						Log.d("Error.Response", error.toString());
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();

				int index = rgGender.getCheckedRadioButtonId();
				View rdBtn = rgGender.findViewById(index);
				int id = rgGender.indexOfChild(rdBtn);
				RadioButton btn = (RadioButton) rgGender.getChildAt(id);
				String gender = btn.getText().toString();

				params.put("usr_name", etName.getText().toString());
				params.put("usr_last_name", etLastName.getText().toString());
				params.put("usr_gender", gender);
				params.put("usr_email", etEmail.getText().toString());
				params.put("usr_address", etAddress.getText().toString());
				params.put("usr_pc", etPostCode.getText().toString());
				params.put("usr_city", etCity.getText().toString());
				params.put("usr_pass", etPass.getText().toString());
				params.put("usr_country", spCountry.getSelectedItem()
						.toString());
				params.put("usr_user_name", etUserName.getText().toString());

				return params;
			}
		};

		AppController.getInstance().addToRequestQueue(postRequest);
	}

	private final class ResponseListener implements DialogListener {
		public void onComplete(Bundle values) {
			adapter.getUserProfileAsync(new ProfileDataListener());

		}

		@Override
		public void onBack() {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity().getApplicationContext(),
					"facebook back", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity().getApplicationContext(),
					"facebook cancel", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onError(SocialAuthError arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity().getApplicationContext(),
					"facebook error", Toast.LENGTH_SHORT).show();

		}

	}

	private final class ProfileDataListener implements
			SocialAuthListener<Profile> {

		@Override
		public void onError(SocialAuthError arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onExecute(String arg0, Profile arg1) {
			Toast.makeText(getActivity().getApplicationContext(),
					" Loged as" + arg1.getFullName(), Toast.LENGTH_SHORT)
					.show();

		}
	}

}