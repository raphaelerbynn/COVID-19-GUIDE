package com.mountain.covid19guide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Prevention extends AppCompatActivity {

    TextView cleanTextView;
    TextView socialTextView;
    TextView crowdedTextView;
    TextView homeTextView;
    TextView touchTextView;

    Button cleanButton;
    Button socialButton;
    Button crowdedButton;
    Button homeButton;
    Button touchButton;


    public void showMore(View view){

        if (view.getId() == R.id.cleanHandButton){
            if (cleanTextView.getVisibility() == View.GONE){

                cleanTextView.setVisibility(View.VISIBLE);
                cleanButton.setText("Hide");

            }
            else{
                cleanTextView.setVisibility(View.GONE);
                cleanButton.setText("More");
            }

        }

        if (view.getId() == R.id.distanceButton){
            if (socialTextView.getVisibility() == View.GONE){

                socialTextView.setVisibility(View.VISIBLE);
                socialButton.setText("Hide");

            }
            else{
                socialTextView.setVisibility(View.GONE);
                socialButton.setText("More");
            }

        }

        if (view.getId() == R.id.crowdedButton){
            if (crowdedTextView.getVisibility() == View.GONE){

                crowdedTextView.setVisibility(View.VISIBLE);
                crowdedButton.setText("Hide");

            }
            else{
                crowdedTextView.setVisibility(View.GONE);
                crowdedButton.setText("More");
            }

        }

        if (view.getId() == R.id.stayHomeButton){
            if (homeTextView.getVisibility() == View.GONE){

                homeTextView.setVisibility(View.VISIBLE);
                homeButton.setText("Hide");

            }
            else{
                homeTextView.setVisibility(View.GONE);
                homeButton.setText("More");
            }

        }

        if (view.getId() == R.id.touchButton){
            if (touchTextView.getVisibility() == View.GONE){

                touchTextView.setVisibility(View.VISIBLE);
                touchButton.setText("Hide");

            }
            else{
                touchTextView.setVisibility(View.GONE);
                touchButton.setText("More");
            }

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevention);


        cleanTextView = findViewById(R.id.cleanTextView);
        socialTextView = findViewById(R.id.distanceTextView);
        crowdedTextView = findViewById(R.id.crowdedTextView);
        homeTextView = findViewById(R.id.stayHomeTextView);
        touchTextView = findViewById(R.id.touchTextView);

        cleanButton = findViewById(R.id.cleanHandButton);
        socialButton = findViewById(R.id.distanceButton);
        crowdedButton = findViewById(R.id.crowdedButton);
        homeButton = findViewById(R.id.stayHomeButton);
        touchButton = findViewById(R.id.touchButton);

        cleanTextView.setVisibility(View.GONE);
        socialTextView.setVisibility(View.GONE);
        crowdedTextView.setVisibility(View.GONE);
        homeTextView.setVisibility(View.GONE);
        touchTextView.setVisibility(View.GONE);


    }



}
