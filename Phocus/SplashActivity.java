package com.multithread.mosiah.projectcs246;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.os.SystemClock.sleep;

/**
 * SplashActivity displays a splash screen image for 3 seconds, then starts the MainActivity
 * Created on 7/1/2017.
 * @author Peyton Dunnaway
 */

public class SplashActivity extends AppCompatActivity {
    /**
     * @param savedInstanceState variable used to create new activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sleep(3000);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
