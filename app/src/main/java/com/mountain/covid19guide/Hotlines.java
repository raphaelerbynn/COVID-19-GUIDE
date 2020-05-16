package com.mountain.covid19guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Hotlines extends AppCompatActivity {

    Intent intent;

    BottomNavigationView bottomNavigationView;


    BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.home:

                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    break;

                case R.id.hotlines:

                    if (bottomNavigationView.getSelectedItemId() != R.id.hotlines) {

                        intent = new Intent(getApplicationContext(), Hotlines.class);
                        startActivity(intent);
                    }
                    break;

                case R.id.search:

                    intent = new Intent(getApplicationContext(), Search.class);
                    startActivity(intent);


                    break;

                case R.id.about:


                    intent = new Intent(getApplicationContext(), About.class);
                    startActivity(intent);


                    break;

                default:
                    break;



            }
            return false;

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_hotline);

        bottomNavigationView = findViewById(R.id.buttomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);




    }
}
