package com.multithread.mosiah.projectcs246;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * MainActivity class is the opening activity which displays a ListView of Task objects, along with
 * Progress Bars, showing how far along a user is from completing the task for the originally designated
 * number of iterations.
 * <p>Holding down a Task item will prompt a delete confirmation.</p>
 * <p>Clicking a Task item once will take user to the Timer activity for that Task.</p>
 *
 * @author Mosiah Querubin
 */
public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button viewStreak;
    private Button reminderButton;

    ArrayList<Task> myTaskList = new ArrayList<>();
    ArrayAdapter<Task> taskArrayAdapter;

    /**
     * Takes care of loading SharedPreferences data and creating onClick listeners for all the
     * Buttons on the activity.
     * @author Mosiah Querubin
     * @param savedInstanceState variable used to create new activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Peyton's code to set task tally for first time to 0
        SharedPreferences firstRun = getSharedPreferences("FirstRun", MODE_PRIVATE);

        if (firstRun.getBoolean("first_run", true)) {
            final SharedPreferences tally = getSharedPreferences("tallyCount", MODE_PRIVATE);
            tally.edit().putInt("tally", 0).commit();
            firstRun.edit().putBoolean("first_run", false).commit();
        }

        viewStreak = (Button) findViewById(R.id.toTally);
        viewStreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TallyActivity.class);
                startActivity(intent);
            }
        });

        //Code for the add task
        button = (Button) findViewById(R.id.addTask);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EditTask.class);
                intent.putExtra("activityOne", "Set a goal");
                startActivity(intent);
            }
        });

        //Code for the reminder
        reminderButton = (Button) findViewById(R.id.reminderButtonId);
        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
                intent.putExtra("reminds", "Set Reminder");
                startActivity(intent);
            }
        });
        //Retrieving the data
        SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
        Type taskListType = new TypeToken<ArrayList<Task>>() {
        }.getType();

        //taskList.edit().remove("MyObjects").commit();
        if (json != null) {

            myTaskList = gson.fromJson(json, taskListType);

            //Calling the custom list view adapter
            taskArrayAdapter = new AdapterTask(this, R.layout.list_row, myTaskList);
            ListView listView = (ListView)findViewById(R.id.listViewId);
            listView.setAdapter(taskArrayAdapter);

            //Creating a listener for each task which then leads to the timer activity
            AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Task task = myTaskList.get(position);
                    Intent intent = new Intent(MainActivity.this, Timer.class);
                    intent.putExtra("taskObject", position);
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(adapterViewListener);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    removeItem(position);
                    return true;
                }
            });
        }
    }

    /**
     * Populate ListView and addTask button when the MainActivity starts
     * @author Mosiah Querubin, Bernhardt Ramat
     */
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = taskList.getString("MyObjects", null); // myTaskList - my list of Tasks objects.
        Type taskListType = new TypeToken<ArrayList<Task>>() {
        }.getType();

        if (json != null) {

            myTaskList = gson.fromJson(json, taskListType);

            //Calling the custom list view adapter
            taskArrayAdapter = new AdapterTask(this, R.layout.list_row, myTaskList);
            ListView listView = (ListView) findViewById(R.id.listViewId);
            listView.setAdapter(taskArrayAdapter);

            //Creating a listener for each task which then leads to the timer activity
            AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Task task = myTaskList.get(position);
                    Intent intent = new Intent(MainActivity.this, Timer.class);
                    intent.putExtra("taskObject", position);
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(adapterViewListener);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    removeItem(position);
                    return true;
                }
            });
        }
    }

    /**
     * Deletes a Task item from the ListView
     * @author Bernhardt Ramat
     * @param position where item to be deleted is in the list
     */
    protected void removeItem(int position) {
        final int deletePosition = position;
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myTaskList.remove(deletePosition);

                SharedPreferences taskList = getSharedPreferences("taskList", MODE_PRIVATE);
                Gson gson = new Gson();
                SharedPreferences.Editor prefsEditor = taskList.edit();
                String json = gson.toJson(myTaskList); // myTaskList - my list of Tasks objects.
                prefsEditor.putString("MyObjects", json);
                prefsEditor.commit();
                taskArrayAdapter.notifyDataSetChanged();
                taskArrayAdapter.notifyDataSetInvalidated();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }
}