package com.multithread.mosiah.projectcs246;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Mosiah on 7/10/2017.
 */

public class MemeActivity extends AppCompatActivity {

    ImageView imgView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);


        imgView = (ImageView) findViewById(R.id.imageView);
        Random rand = new Random();
        int rndInt = rand.nextInt(5) + 1; // n = the number of images, that start at idx 1
        String imgName = "img" + rndInt;
        int id = getResources().getIdentifier(imgName, "drawable", getPackageName());
        imgView.setImageResource(id);

        button = (Button) findViewById(R.id.OK);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MemeActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });


    }
}
