package com.tae.store;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tae.store.adapters.NavDrawerListAdapter;
import com.tae.store.fragments.BagFragment;
import com.tae.store.fragments.CategoryFragment;
import com.tae.store.fragments.CustomMapFragment;
import com.tae.store.fragments.HomeFragment;
import com.tae.store.fragments.MyTaeFragment;
import com.tae.store.fragments.NoConnectionFragment;
import com.tae.store.helpers.DatabaseHandler;
import com.tae.store.model.Category;
import com.tae.store.model.NavDrawerItem;
import com.tae.store.model.Product;
import com.tae.store.utilities.NetworkCheckService;
import com.tae.store.utilities.PayPalUtil;
import com.tae.store.utilities.SPTags;
import com.tae.store.utilities.ServerUrl;
import com.tae.store.utilities.ServerUtilities;
import com.tae.store.utilities.WakeLocker;

/**
 * Main activity where all the fragment will be placed. Manage the screen
 * rotation and the back-button feature to allow the user navigate through the
 * app.
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
@SuppressLint({ "InlinedApi", "NewApi" })
public class MainActivity extends Activity {

	// Fragments
	static public android.app.FragmentManager fragmentManager;
	/** Store the tag of the current fragment showed */
	static private String currentFragment;
	/** Store the current fragment showed */
	static public Fragment fragment;
	/** Store the fragment's back stack tags */
	static public ArrayList<String> backStackFragment;
	/** Store the root category (men or women) */
	static public String ROOT_CATEGORY;;
	/** Store the category ID */
	static public String CATEGORY;

	
	private AsyncTask<Void, Void, Void> mRegisterTask;
	
	
	/** Continuous network checker */
	private NetworkCheckReceiver netReceiver;

	// Slide Menu
	/** Drawer menu */
	private DrawerLayout mDrawerLayout;
	/** List where the menu elements will be placed */
	private ListView mDrawerList;
	/**  */
	private ActionBarDrawerToggle mDrawerToggle;
	/** Adapter for the menu elements */
	private NavDrawerListAdapter adapter;
	/** Array to store the titles of the menu's elements */
	private String[] navMenuTitles;
	/** Array to store the icons of the menu's elements */
	private TypedArray navMenuIcons;
	/** Array to store <i>NavDraweItem</i> for the menu */
	private ArrayList<NavDrawerItem> navDrawerItems;

	private ArrayList<Category> men;

	private ArrayList<Category> women;

	private ArrayList<Product> offers;

	/**
	 * Create the menu, customize the action bar and launch the services for
	 * PayPal and Network Checker.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent prev_screen = getIntent();

		// Slide menu
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.openDrawer(Gravity.START);
		mDrawerLayout.closeDrawer(Gravity.START);

		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		navDrawerItems = new ArrayList<NavDrawerItem>();
		for (int i = 0; i < navMenuTitles.length; i++) {
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons
					.getResourceId(i, -1), false, ""));
		}
		navMenuIcons.recycle();

		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// Enabling action bar app icon and behaving it as toggle button
		getActionBar().show();
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(R.drawable.ic_drawer);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b40909")));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
				R.string.app_name, R.string.main_menu) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle("");
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("");
				invalidateOptionsMenu();
			}
		};
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		fragmentManager = getFragmentManager();
		if (savedInstanceState != null) {
			// Restore the fragment's instance
			currentFragment = savedInstanceState.getString("currentFragment");
			// Log.v("Stack", "Current fragment: " + currentFragment);
			fragment = fragmentManager.getFragment(savedInstanceState, currentFragment);
			// Log.v("Stack", "fragment: " + fragment.toString());
			replaceFragment(fragment, currentFragment, false);
		} else {
			// Load first fragment
			backStackFragment = new ArrayList<String>();
			// Get the data previously requested to the server, from the intent
			men = prev_screen.getParcelableArrayListExtra("men");
			women = prev_screen.getParcelableArrayListExtra("women");
			offers = prev_screen.getParcelableArrayListExtra("offer");

			fragment = new HomeFragment(this, men, women, offers);
			addFragment(fragment, "HOME_FRAGMENT");
		}

		// Create the network checker
		netReceiver = new NetworkCheckReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("statusUpdate");
		this.registerReceiver(netReceiver, filter);
		
		registerDevice();

		// Network status
		Intent intent = new Intent(getApplicationContext(), NetworkCheckService.class);
		startService(intent);

		// PayPal configuration
		Intent paypalIntent = new Intent(this, PayPalService.class);
		paypalIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalUtil.config);
		startService(paypalIntent);

	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Manage the actions to perform when the user press one icon on the action
	 * bar.
	 */
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		if (item.getItemId() == R.id.option_bag) {
			replaceFragment(new BagFragment(), "BAG_FRAGMENT", true);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(android.view.Menu menu) {

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * Add one fragment to the activity.
	 * 
	 * @param fragment
	 *            Fragment that will be added.
	 * @param tag
	 *            Tag associated to the fragment.
	 */
	private void addFragment(Fragment fragment, String tag) {
		if (fragment != null) {
			fragmentManager.beginTransaction().add(R.id.frame_container, fragment).commit();
			currentFragment = tag;
			backStackFragment.add(currentFragment);
		}
	}

	/**
	 * Replace the current fragment with the fragment passed as parameter. This
	 * method is static to allow other fragment change themselves. Add to
	 * <i>backStackFragment<i> the tag passed as parameter.
	 * 
	 * @param fragment
	 *            Fragment that will be displayed.
	 * @param tag
	 *            Tag associated to the fragment.
	 * @param backStack
	 *            True if the fragment will be added to the back stack.
	 */
	static public void replaceFragment(Fragment fragment, String tag, boolean backStack) {
		if (fragment != null) {
			currentFragment = tag;
			MainActivity.fragment = fragment;
			if (backStack) {
				fragmentManager.beginTransaction()
						.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
						.replace(R.id.frame_container, fragment, tag).addToBackStack(null).commit();

			} else {
				fragmentManager.beginTransaction()
						.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
						.replace(R.id.frame_container, fragment, tag).commit();
			}
			if (!backStackFragment.get(backStackFragment.size() - 1).matches(currentFragment)) {
				backStackFragment.add(currentFragment);
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putStringArrayList("backstackFragment", backStackFragment);
		Log.v("SAVE", backStackFragment.toString());
		outState.putString("currentFragment", currentFragment);
		Log.v("SAVE", currentFragment);
		Log.v("SAVE", fragment.toString());
		fragmentManager.putFragment(outState, currentFragment, fragment);
		// Log.v("Stack", "onSaveInstance: " + currentFragment);
	}

	/**
	 * Change the fragment displayed on the activity according the parameter
	 * received. This method is called each time that the user press a button on
	 * the menu.
	 * 
	 * @param position
	 *            Position of the element tapped on the menu.
	 */
	public void displayView(int position) {
		String fragmentName = "";
		switch (position) {
		case 0:
			ArrayList<Category> men = getIntent().getParcelableArrayListExtra("men");
			ArrayList<Category> women = getIntent().getParcelableArrayListExtra("women");
			ArrayList<Product> offers = getIntent().getParcelableArrayListExtra("offer");

			fragment = new HomeFragment(this, men, women, offers);
			fragmentName = "HOME_FRAGMENT";
			break;
		case 1:
			fragment = new CategoryFragment("Men");
			fragmentName = "CATEGORY_FRAGMENT";
			break;
		case 2:
			fragment = new CategoryFragment("Women");
			fragmentName = "CATEGORY_FRAGMENT";
			break;
		case 3:
			fragment = new CustomMapFragment();
			fragmentName = "MAP_FRAGMENT";
			break;
		case 4:

			fragment = new MyTaeFragment();
			fragmentName = "MY_TAE_FARGMENT";
			break;
		case 5:
			// Share
			String shareBody = getResources().getString(R.string.share_body);
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					getResources().getString(R.string.share_subjet));
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent,
					getResources().getString(R.string.share_with)));
			return;
		default:
			break;
		}

		replaceFragment(fragment, fragmentName, true);
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	/**
	 * Private class that implements OnItemClickListener interface for the menu.
	 * 
	 * @author Jose Garcia
	 * @version 1.0
	 * @since 2014-10-08
	 */
	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			displayView(position);
		}
	}

	/**
	 * This method is called each time that the user press back button. Remove
	 * the last element of <i>backStackFragment</i> or finish the app if there's
	 * not elements to remove.
	 */
	@Override
	public void onBackPressed() {
		if (backStackFragment.size() > 1) {
			// Log.v("BACK", "Current fragment before remove: " +
			// currentFragment);
			// Log.v("BACK", backStackFragment.toString());
			backStackFragment.remove(backStackFragment.size() - 1);

			currentFragment = backStackFragment.get(backStackFragment.size() - 1);
			fragment = fragmentManager.findFragmentByTag(currentFragment);

			// Log.v("BACK", "Current fragment after remove: " +
			// currentFragment);
			// Log.v("BACK", backStackFragment.toString());
		} else {
			finish();
		}
		// Log.v("Stack", "onBackPressed: " + currentFragment);
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		unregisterReceiver(netReceiver);
		unregisterReceiver(mHandleMessageReceiver);
		GCMRegistrar.onDestroy(this);
		stopService(new Intent(this, PayPalService.class));

		super.onDestroy();
	}

	
 
	
	
	public void registerDevice(){
		
		
		// Make sure the device has the proper dependencies.
		 GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		//lblMessage = (TextView) findViewById(R.id.lblMessage);
		
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				ServerUrl.DISPLAY_MESSAGE_ACTION));
		
		
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);
		
		SharedPreferences preferences = getPreferences(MainActivity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(SPTags.GCM_ID, regId).commit();
		Log.v("GCM", "ID: "+regId);
		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
			GCMRegistrar.register(this, ServerUrl.SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.				
				Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, "", "", regId);
						SharedPreferences preferences = getPreferences(MainActivity.MODE_PRIVATE);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(SPTags.GCM_ID, regId).commit();
						editor.putBoolean(SPTags.ALL_GCM_DATA, false);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
		
	}
	
	
	/**
	 * This method manage the action to perform according the parameters
	 * received when <i>StartActivityForResult</i> is called. It's needed
	 * override this method to manage the response given by PayPal.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Log.i("PayPal", "onActivityResult: " + requestCode + " : " +
		// resultCode);
		if (resultCode == Activity.RESULT_OK) {
			PaymentConfirmation confirm = data
					.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			if (confirm != null) {
				DatabaseHandler db = new DatabaseHandler(this);
				db.deleteAll();

				Toast.makeText(getApplicationContext(),
						"PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG).show();

				((BagFragment) fragment).cleanScreen();
			}
		} else if (resultCode == Activity.RESULT_CANCELED) {

		} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

		}
	}

	

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(ServerUrl.EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message
			//lblMessage.append(newMessage + "\n");			
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};
	
	
	/**
	 * BroadcastReveiver that, in case there's not Internet, display the
	 * respective fragment to alert to the user that he needs an Internet
	 * connection to continue using the app.
	 * 
	 * @author Jose Garcia
	 * @version 1.0
	 * @since 2014-10-08
	 */
	class NetworkCheckReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			try {
				String st = arg1.getStringExtra("networkstatus");
				if (st.equals("Not connected to Internet") && (currentFragment != "NO_CONNECTION")) {
					replaceFragment(new NoConnectionFragment(currentFragment, fragment),
							"NO_CONNECTION", false);
				}
			}

			catch (Exception e) {
				Log.e("error", e.toString());
				Toast.makeText(getApplicationContext(), "Exception in reading status data",
						Toast.LENGTH_SHORT).show();
			}

		}
	}
}
