package com.tae.store.utilities;

import com.paypal.android.sdk.payments.PayPalConfiguration;

public class PayPalUtil {

	public static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
	public static final String CONFIG_CLIENT_ID = "AYHhihBRVUpBAzEa8eW1iMywzluZlETdnCNMtKCl05mOeSvP5AU4bHRSzNXY";
	public static final String CURRENCY = "GBP";
	public static final int REQUEST_CODE_PAYMENT = 1;
	public static PayPalConfiguration config = new PayPalConfiguration()
			.environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID);
	public static final String SHIPPING = "7.50";

}
