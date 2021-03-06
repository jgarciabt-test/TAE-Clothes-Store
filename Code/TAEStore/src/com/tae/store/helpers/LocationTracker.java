package com.tae.store.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;


/**
* @author      Jose Garcia 
* @version     1.0                 
* @since       2014-10-08         
*/
public class LocationTracker implements LocationListener {

	private final Context mContext;

	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;

	protected LocationManager locationManager;
	Location location;
	double latitude;
	double longitude;

	private static final long MIN_DIST_TO_UPDATE = 25; // 25 meters
	private static final long MIN_TIME_UPDATE = 60000; // 1 minute

	public LocationTracker(Context context) {
		mContext = context;
	}

	/**
	 * Try to get the location of the device. If it's possible, store latitude and longitude in class attribute.
	 *
	 */
	public void getLocation() {
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(Context.LOCATION_SERVICE);
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				Toast.makeText(mContext, "GPS possition is not available",
						Toast.LENGTH_SHORT).show();
			} else {
				canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, MIN_TIME_UPDATE,
							MIN_DIST_TO_UPDATE, this);
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
					return;
				}
				if (isGPSEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE,
							MIN_DIST_TO_UPDATE, this);
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove all the location updates asociated to the class.
	 */
	public void stopGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
	}

	/**
	 * Get the latitude of the device if it's possible.
	 * @return  Latitude.
	 */
	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}
		return latitude;
	}

	/**
	 * Get the longitude of the device if it's possible.
	 * @return  Longitude.
	 */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}
		return longitude;
	}

	/**
	 * Get if it's possible to get the location of the device
	 * @return  true if it's possible.
	 */
	public boolean canGetLocation() {
		return canGetLocation;
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

}
