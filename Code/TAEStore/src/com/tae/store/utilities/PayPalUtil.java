package com.tae.store.utilities;

import com.paypal.android.sdk.payments.PayPalConfiguration;

/** Class with parameters to customize the payment with PayPal. Includes the API KEY and the shipping costs.
 * 
* @author      Jose Garcia 
* @version     1.0                 
* @since       2014-10-08         
*/
public class PayPalUtil {

	public static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
	public static final String CONFIG_CLIENT_ID = "AYHhihBRVUpBAzEa8eW1iMywzluZlETdnCNMtKCl05mOeSvP5AU4bHRSzNXY";
	public static final String CURRENCY = "GBP";
	public static final int REQUEST_CODE_PAYMENT = 1;
	public static PayPalConfiguration config = new PayPalConfiguration()
			.environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID);
	public static final String SHIPPING = "7.50";

}
