package com.mountain.covid19guide;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    static Intent intent;

    TextView casesTextView;
    TextView deathsTextView;
    TextView recoveriesTextView;
    TextView numOfCases;
    TextView numOfDeaths;
    TextView numOfRecovered;

    BottomNavigationView bottomNavigationView;
    NestedScrollView nestedScrollView;




    int cases;
    int deaths;
    int recovered;

    String data = "";




    BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.home:

                    if (bottomNavigationView.getSelectedItemId() != R.id.home) {

                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    break;

                case R.id.hotlines:

                    if (bottomNavigationView.getSelectedItemId() != R.id.hotlines) {

                        intent = new Intent(getApplicationContext(), Hotlines.class);
                        startActivity(intent);

                    }

                    return true;


                case R.id.search:

                    if (bottomNavigationView.getSelectedItemId() != R.id.search) {

                        intent = new Intent(getApplicationContext(), Search.class);
                        startActivity(intent);

                    }
                    return true;

                case R.id.about:

                    if (bottomNavigationView.getSelectedItemId() != R.id.about) {

                        intent = new Intent(getApplicationContext(), About.class);
                        startActivity(intent);

                    }
                    return true;

                default:
                    return false;



            }
            return false;

        }
    };



    public void click(View view)  {

        if (view.getId() == R.id.countriesCardView){
            intent = new Intent(getApplicationContext(), Countries.class);
            startActivity(intent);
        }
//        if (view.getId() == R.id.countriesCardView){
//            intent = new Intent(getApplicationContext(), Countries.class);
//            startActivity(intent);
//        }
//        if (view.getId() == R.id.countriesCardView){
//            intent = new Intent(getApplicationContext(), Countries.class);
//            startActivity(intent);
//        }
//        if (view.getId() == R.id.countriesCardView){
//            intent = new Intent(getApplicationContext(), Countries.class);
//            startActivity(intent);
//        }
//        if (view.getId() == R.id.countriesCardView){
//            intent = new Intent(getApplicationContext(), Countries.class);
//            startActivity(intent);
//        }
//

    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_bar);

//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(0);
//        menuItem.setChecked(true);





        casesTextView = findViewById(R.id.caesTextView);
        deathsTextView = findViewById(R.id.deathsTextView);
        recoveriesTextView = findViewById(R.id.recoveredTextView);
        recoveriesTextView = findViewById(R.id.recoveredTextView);
        numOfCases = findViewById(R.id.numOfCases);
        numOfDeaths = findViewById(R.id.numOfDeaths);
        numOfRecovered = findViewById(R.id.numOfRecoveries);




        nestedScrollView = findViewById(R.id.nestedScrollView);


        bottomNavigationView = findViewById(R.id.buttomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);





        if(internetIsAvailable()){

            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute();

        }
        else {

            numOfCases.setText("...");
            numOfDeaths.setText("...");
            numOfRecovered.setText("...");


        }


//        Log.i("bottomvav", String.valueOf(bottomNavigationView.getHeight()));

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setMargins(0, 0,0,0);
        bottomNavigationView.setLayoutParams(layoutParams);
//
        layoutParams.setBehavior(new BottomNavBehavior());





    }


    protected boolean internetIsAvailable() {

        boolean have_Wifi = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos) {

            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if (info.isConnected()) {
                    have_Wifi = true;
                }
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (info.isConnected()) {
                    have_MobileData = true;
                }
            }

        }

        return have_Wifi || have_MobileData;

    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    class DownloadTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            try {

                URL url = new URL("https://coronavirus-19-api.herokuapp.com/all");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String lineData = "";


                while (lineData != null) {

                    lineData = bufferedReader.readLine();
                    data += lineData;

                }




                JSONObject jsonObject = new JSONObject(data);



                cases = (int) jsonObject.get("cases");
                deaths = (int) jsonObject.get("deaths");
                recovered = (int) jsonObject.get("recovered");


                Log.i("totalNumbers", String.valueOf(cases));



            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            numOfCases.setText(String.valueOf(cases));
            numOfDeaths.setText(String.valueOf(deaths));
            numOfRecovered.setText(String.valueOf(recovered));


        }
    }



    class BottomNavBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {



        @Override
        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target, int axes) {
            return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
        }

        @Override
        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

            if (dy < 0){
                showBottomNav(child);
                Log.i("nav", "show");
            }
            else if (dy > 0){
                hideBottomNav(child);
                Log.i("nav", "hide");
            }

        }

        public void hideBottomNav(BottomNavigationView view){
            view.animate().translationY(view.getHeight());
        }
        public void showBottomNav(BottomNavigationView view){
            view.animate().translationY(0);
        }
    }





}
