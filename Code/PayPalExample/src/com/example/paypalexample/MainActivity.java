package com.example.paypalexample;

import java.math.BigDecimal;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class MainActivity extends Activity {

	// Can be NO_NETWORK for OFFLINE, SANDBOX for TESTING and LIVE for
	// PRODUCTION
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
	// note that these credentials will differ between live & sandbox
	// environments.
	private static final String CONFIG_CLIENT_ID = "AYHhihBRVUpBAzEa8eW1iMywzluZlETdnCNMtKCl05mOeSvP5AU4bHRSzNXY";
	// when testing in sandbox, this is likely the -facilitator email address.
	private static final String CONFIG_RECEIVER_EMAIL = "";

	private static final int REQUEST_CODE_PAYMENT = 1;
	
	
	private static PayPalConfiguration config = new PayPalConfiguration()
			.environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);
	}

	public void buy(View v) {
		PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal("1.74"), "USD", "Panete", PayPalPayment.PAYMENT_INTENT_SALE);
	
		Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == Activity.RESULT_OK) {
	    PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
	    if (confirm != null) {
	    	 try {
                 Log.i("PAYPAL", confirm.toJSONObject().toString(4));
                 Log.i("PAYPAL", confirm.getPayment().toJSONObject().toString(4));
                 /**
                  *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                  * or consent completion.
                  * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                  * for more details.
                  *
                  * For sample mobile backend interactions, see
                  * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                  */
                 Toast.makeText(
                         getApplicationContext(),
                         "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
                         .show();

             } catch (JSONException e) {
                 Log.e("PAYPAL", "an extremely unlikely failure occurred: ", e);
             }
	    }
	  } else if (resultCode == Activity.RESULT_CANCELED) {
	    // Show the user that this got canceled
	  } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
	    // Check the docs ;)
	  }
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy() {
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}
}
