package com.mountain.covid19guide;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    TextView casesTextView;
    TextView deathsTextView;
    TextView recoveriesTextView;
    TextView numOfCases;
    TextView numOfDeaths;
    TextView numOfRecovered;

    int cases;
    int deaths;
    int recovered;

    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        casesTextView = findViewById(R.id.caesTextView);
        deathsTextView = findViewById(R.id.deathsTextView);
        recoveriesTextView = findViewById(R.id.recoveredTextView);
        numOfCases = findViewById(R.id.numOfCases);
        numOfDeaths = findViewById(R.id.numOfDeaths);
        numOfRecovered = findViewById(R.id.numOfRecoveries);


        if(internetIsAvailable()){

            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute();

        }
        else {

            numOfCases.setText("...");
            numOfDeaths.setText("...");
            numOfRecovered.setText("...");


        }


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


    class DownloadTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            try {

                URL url = new URL("https://coronavirus-19-api.herokuapp.com/all/");
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

}
