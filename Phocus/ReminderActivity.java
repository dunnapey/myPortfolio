package com.multithread.mosiah.projectcs246;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Bernhardt on 07/13/2017
 */
public class ReminderActivity extends AppCompatActivity {

    //Declarations
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private Button setButton;
    private Button cancelButton;
    private TextView alarmTextView;
    private EditText reminderNote;;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        this.context = this;

        alarmTextView = (TextView) findViewById(R.id.alarmTextId);

        //Creating an intent that takes you to the Alarm receiver class
        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        //Get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmTimePicker = (TimePicker)findViewById(R.id.timePickerId);
        setButton = (Button)findViewById(R.id.setButtonId);
        cancelButton = (Button)findViewById(R.id.cancelAlarmButtonId);

        //Create an instance of the calendar
        final Calendar calendar = Calendar.getInstance();

        //Initializing the reminder note
        reminderNote = (EditText)findViewById(R.id.editReminderTextId);

        //Creating a listener for the set button
        setButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Toast.makeText(ReminderActivity.this, "Alarm On", Toast.LENGTH_LONG).show();

                //Setting the hour and minutes
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                //creating variables in order to be able to display the alarm time set
                int hour = alarmTimePicker.getHour();
                int minute = alarmTimePicker.getMinute();
                String minute_string = String.valueOf(minute);

                //Converting from 24 hours clock
                // format to a 12 hour format
                if (hour > 12) {
                    hour = hour - 12;
                }

                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                //Saving the reminder note into shared preferences
                String note = reminderNote.getText().toString();
                SharedPreferences prefNotes = getSharedPreferences("reminders", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefNotes.edit();
                prefsEditor.putString("MyReminders", note);
                prefsEditor.commit();

                //Displaying the alarm time
                setAlarmText("Reminder Alarm for " + prefNotes.getString("MyReminders", note) + " set to; " + hour + ":" + minute_string);

                //put in extra string into my intent
                myIntent.putExtra("extra", "alarm on");
                myIntent.putExtra("notes", note);

                //Creating a pending intent for the alarm
                pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                //Setting a command for the alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        });

        //Setting an onclick listener for the cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Putting and extra intent
                myIntent.putExtra("extra", "alarm off");

                //Checking that there was a pending intent
                // before the user presses the cancel button
                if (pendingIntent != null)
                    alarmManager.cancel(pendingIntent);

                //This send a broadcast to
                //stop the alarm after it has gone off
                sendBroadcast(myIntent);

                //Notifying the user that they have
                // pressed the Alarm cancel button
                setAlarmText("Alarm canceled");

                Toast.makeText(ReminderActivity.this, "Alarm Cancelled", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * This function is used to display the text
     * @param alarmText
     */
    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }


}
