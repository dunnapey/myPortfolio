package com.multithread.mosiah.projectcs246;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * This class provides the service of an alarm.
 * @author Bernhardt
 */
public class RingtonePlayingService extends Service {

    //Declarations
    MediaPlayer mediaSong;
    int startId;
    boolean isRunning;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * This function creates the starting command for the alarm
     * @param intent
     * @param flags
     * @param startId
     * @return START_NOT_STICKY
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //fetch the extra string
        String state = intent.getExtras().getString("extra");
        Log.e("Ringtone extra is ", state);

        //Fetch the reminder note
        String myNote = intent.getExtras().getString("notes");

        //Assigning states of the alarm to the
        //start command
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("StartId is ", state);
                break;
            default:
                startId = 0;
                break;
        }

        //When the alarm is off and the user presses on
        if (!this.isRunning && startId == 1) {

            Log.e("There's no music ", "and you want to start");
            //Create an instance of the media player
            mediaSong = MediaPlayer.create(this, R.raw.gdfr);
            mediaSong.start();

            //Reassigning isRunning and the startId
            this.isRunning = true;
            this.startId = 0;

            //set up an intent that goes to the reminder activity
            Intent alarmIntent = new Intent(this, ReminderActivity.class);

            //set up a pending intent
            PendingIntent pendingIntent2 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), alarmIntent, 0);

            //Make notification parameters
            Notification popup = new NotificationCompat.Builder(this)
                    .setContentTitle("Phocus Reminder!")
                    .setContentText("Do your " + myNote + " task")
                    .setSmallIcon(R.drawable.splash)
                    .setContentIntent(pendingIntent2)
                    .setAutoCancel(true)
                    .build();

            //Creating the notification manager
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, popup);
        }

        //The alarm is on and the user presses the cancel button
        else if (this.isRunning && startId == 0) {
            Log.e("There's music ", "and you want it to end");
            //Stopping the music from playing
            //and resetting the variables
            mediaSong.stop();
            this.isRunning = false;
            this.startId = 0;
        }

        //The alarm is off and the user presses the cancel button
        else if (!this.isRunning && startId == 0) {
            Log.e("No music ", "you still pressed cancel");
            this.isRunning = false;
            this.startId = 0;
        }

        //The alarm is on and the user presses the set button
        else if (this.isRunning && startId == 1) {
            this.isRunning = true;
            this.startId = 1;
        }

        //Otherwise do nothing
        else {

        }

        //Makes sure that the app doesn't remain stagnant
        return START_NOT_STICKY;
    }

    //In-case the user exists the app then the
    //alarm is silenced
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }
}






