package com.multithread.mosiah.projectcs246;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by bernhardtramat on 7/11/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    /**
     * This acts as an adapter between the ReminderActivity class and
     * the RingtonePlayingService class
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the receiver.", "Yay!");
        //fetch the extra string from the intent
        String getMyString = intent.getExtras().getString("extra");

        Log.e("what is the key? ", getMyString);
        //Fetch the note from the intent
        String getMyNote = intent.getExtras().getString("notes");

        /**
         * this creates an intent that links the alarm manager
         * to the ringtone service
         */
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        //pass the extra string
        serviceIntent.putExtra("extra", getMyString);
        serviceIntent.putExtra("notes", getMyNote);
        context.startService(serviceIntent);
    }
}
