package com.tae.store.utilities;

import android.content.Context;
import android.content.Intent;

public class DisplayMessage {

	
    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(ServerUrl.DISPLAY_MESSAGE_ACTION);
        intent.putExtra(ServerUrl.EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
	
}
