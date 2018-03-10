package com.multithread.mosiah.projectcs246;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bernhardt and Mosiah on 6/27/2017.
 * This class allows for the listview to display the data that we need.
 */
 public class AdapterTask extends ArrayAdapter<Task> {
       private Context context;
        private ArrayList<Task> myTasks;

        //Constructor
        public AdapterTask(Context context, int resource, ArrayList<Task> objects) {
            super(context, resource, objects);
            this.context = context;
            this.myTasks = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent) {

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.list_row, null);

            TextView titleTitle = (TextView) view.findViewById(R.id.titleTask);
            TextView repeat = (TextView)view.findViewById(R.id.repetitionsId);
            ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

            //magical piece of code written by mosiah to fix the bug which prevented us
            // from selecting the proper task based on what list row is clicked.
            final Task theTask = myTasks.get(position);
            int rep = theTask.getIteration();
            String reps = String.valueOf(rep);
            titleTitle.setText(theTask.getTaskName());
            repeat.setText("Repetitions " + reps);
            progressBar.setMax(theTask.getOriginalIteration());

            progressBar.setProgress(rep);
            return view;
        }
    }