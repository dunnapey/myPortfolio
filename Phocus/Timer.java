package com.multithread.mosiah.projectcs246;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Timer class definition.
 * @author Mosiah Querubin
 */
public class Timer extends AppCompatActivity {
    TextView tvTaskName;
    TextView tvTimer;

    Button bStart;

    CountDownTimer timer;
    long remainingTime;
    ArrayList<Task> myTaskList = null;
    int position;
    Task task;
    int count;
    int tallyCount;
    private boolean mIsPaused = true;

    /**
     * Method displays values in Task as a Timer format, ready to begin countdown
     * @author Mosiah Querubin
     * @param savedInstanceState variable to start new activity instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //load from shared preferences.
        SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
        Type taskListType = new TypeToken<ArrayList<Task>>() {
        }.getType();

        //fill myTaskList from the json stored on shared prefs.
        if (json != null)
            myTaskList = gson.fromJson(json, taskListType);


        //grab the position(where the user clicked from the listview in main activity)
        Intent intent = getIntent();

        position = intent.getIntExtra("taskObject", 0);
        task = myTaskList.get(position);

        count = task.getIteration();

        //set textview to the name of the task
        tvTaskName = (TextView) findViewById(R.id.taskDetailId);
        tvTaskName.setPadding(50,50,50,50);
        tvTaskName.setText(task.getTaskName());

        //set textview to the duration of the task
        tvTimer = (TextView) findViewById(R.id.textView);
        int seconds = (int) (task.getDuration() / 1000) % 60;
        int minutes = (int) ((task.getDuration() / (1000 * 60)) % 60);
        int hours = (int) ((task.getDuration() / (1000 * 60 * 60)) % 24);
        tvTimer.setText("" + String.format("%02d:%02d:%02d", hours, minutes, seconds));

        bStart = (Button) findViewById(R.id.start);
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mIsPaused) {
                    bStart.setText("Pause");
                    initTime();
                } else {
                    bStart.setText("Start");
                    cancelTimer();
                }

                mIsPaused = !mIsPaused;
            }
        });

        remainingTime = task.getDuration();
    }

    /**
     * Start timer method which will update remaining duration and iterations
     * @author Mosiah Querubin
     *
     */
    public void initTime() {

        if (task.getIteration() == 0)
           return;

        timer = new CountDownTimer(remainingTime,1000) {
            /**
             * Method to break down time remaining as clock counts down
             * @author Mosiah Querubin
             * @param millisUntilFinished time remaining in milliseconds
             */
            @Override
            public void onTick(long millisUntilFinished) {
                String timeH = String.valueOf(millisUntilFinished/10000);
                String time = String.valueOf(millisUntilFinished/ 1000);
                String timeMill = String.valueOf(millisUntilFinished/100);

                if (remainingTime != 0 && task != null) {

                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);

                    tvTimer.setText("" + String.format("%02d:%02d:%02d", hours, minutes, seconds));

                    remainingTime = millisUntilFinished;
                }
                else
                    return;
            }

            /**
             * Stuff to do when Timer countdown is finished
             * <ul>
             *     <li>Set iterations left</li>
             *     <li>Set duration left</li>
             *     <li>Play finished tone/alarm</li>
             *     <li>Save remaining iterations/duration to SharedPreferences</li>
             *     <li>Return to MainActivity if iterations reach zero</li>
             * </ul>
             * @author Mosiah Querubin
             */
            @Override
            public void onFinish() {
                bStart.performClick();

                task.setIteration(task.getIteration() - 1);
                tvTimer.setText("Repetitions left: " + String.valueOf(task.getIteration()));

                if (task.getIteration() != 0)
                    remainingTime = task.getDuration();

                ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, (ToneGenerator.MAX_VOLUME * 3));
                if (task.getIteration() >= 0 && count > 0) {
                    tone.startTone(ToneGenerator.TONE_CDMA_CALL_SIGNAL_ISDN_INTERGROUP);
                    --count;
                }

                //Peyton's code to increase and save completed task tally
                SharedPreferences tally = getSharedPreferences("tallyCount", MODE_PRIVATE);
                tallyCount = tally.getInt("tally", 0);
                tally.edit().putInt("tally", tallyCount+1).commit();

                SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
                Type taskListType = new TypeToken<ArrayList<Task>>() {
                }.getType();

                //fill myTaskList from the json stored on shared prefs.
                if (json != null) {
                    myTaskList = gson.fromJson(json, taskListType);

                    if (task.getIteration() == 0 && position <= myTaskList.size()) {
                        myTaskList.remove(position);
                        position = myTaskList.size() + 2;
                        Intent intent = new Intent(Timer.this, MemeActivity.class);
                        startActivity(intent);
                    }
                    else if (position <= myTaskList.size())
                      myTaskList.set(position, task);

                    //storing object to shared preferences in json form.
                    SharedPreferences.Editor prefsEditor = taskList.edit();
                    String myjson = gson.toJson(myTaskList); // myTaskList - my list of Tasks objects.
                    prefsEditor.putString("MyObjects", myjson);
                    prefsEditor.commit();
                }
            }
        };
        timer.start();
    }

    /**
     * Method to pause timer countdown
     *@author Mosiah Querubin
     */
    public void cancelTimer() {
        if(timer != null) {
            timer.cancel();
        }
    }

    /**
     * Stuff to do when activity stops (moves to new Activity)
     * @author Mosiah Querubin
     */
    @Override
    protected void onStop() {
        super.onStop();
    }
}
