package com.mountain.covid19guide;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class HomeFragment extends Fragment {

    TextView casesTextView;
    TextView deathsTextView;
    TextView recoveriesTextView;
    private TextView numOfCases;
    private TextView numOfDeaths;
    private TextView numOfRecovered;

    private int cases;
    private int deaths;
    private int recovered;

    private String data = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        casesTextView = rootView.findViewById(R.id.caesTextView);
        deathsTextView = rootView.findViewById(R.id.deathsTextView);
        recoveriesTextView = rootView.findViewById(R.id.recoveredTextView);
        recoveriesTextView = rootView.findViewById(R.id.recoveredTextView);
        numOfCases = rootView.findViewById(R.id.numOfCases);
        numOfDeaths = rootView.findViewById(R.id.numOfDeaths);
        numOfRecovered = rootView.findViewById(R.id.numOfRecoveries);

        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute();

//        numOfCases.setText("...");
//        numOfDeaths.setText("...");
//        numOfRecovered.setText("...");

        return rootView;
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

            numOfCases.setText(NumberFormat.getInstance(Locale.US).format(cases));
            numOfDeaths.setText(NumberFormat.getInstance(Locale.US).format(deaths));
            numOfRecovered.setText(NumberFormat.getInstance(Locale.US).format(recovered));

        }
    }

}
