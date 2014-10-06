package com.tae.store.utilities;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
* @author      Jose Garcia 
* @version     1.0                 
* @since       2014-10-08         
*/
public class NetworkCheckService extends Service {

	String status;
	boolean netStatus;
	NetworkCheckThread networkCheckThread;

	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		networkCheckThread = new NetworkCheckThread();
		networkCheckThread.start();
		return 0;
	}

	/**
	 * service destroy method for clean up actions
	 */
	@Override
	public void onDestroy() {
		networkCheckThread.stopThread();
		super.onDestroy();
	}

	/**
		 * 
		 * 
		 *
		 */
	class NetworkCheckThread extends Thread {

		public NetworkCheckThread() {
			super();
		}

		/**
		 * stop thread method
		 */
		public void stopThread() {
		}

		@Override
		public void run() {
			super.run();

			while (true) {
				status = NetworkUtil
						.getConnectivityStatusString(getApplicationContext());
				sendBroadcast(new Intent().putExtra("networkstatus", status)
						.setAction("statusUpdate"));
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
