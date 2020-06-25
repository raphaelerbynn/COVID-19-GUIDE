package com.mountain.covid19guide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Countries extends AppCompatActivity {

    EditText searchText;
    static ListView listView;

    static MyLisAdapter myLisAdapter;

    CountryStat countryStat;
    ArrayList<CountryStat> arrayList = new ArrayList<CountryStat>();

    int numOfCountries = 230;

    String[] country_name = new String[numOfCountries];
    String[] total_cases = new String[numOfCountries];
    String[] deaths = new String[numOfCountries];
    String[] recovered = new String[numOfCountries];
    String[] today_cases = new String[numOfCountries];
    String[] today_deaths = new String[numOfCountries];
    String[] active = new String[numOfCountries];
    String[] critical = new String[numOfCountries];





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        listView = findViewById(R.id.listView);







        if(internetIsAvailable()){

            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute();



        }
        else {

            Log.i("no data", "no data");


        }


        myLisAdapter = new MyLisAdapter(this, arrayList);
        listView.setTextFilterEnabled(true);
        searchText = findViewById(R.id.search);

        listView.setAdapter(myLisAdapter);



        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String search_text = s.toString();
                ArrayList<CountryStat> arraySearchList = new ArrayList<>();
                for (int i=0; i<arrayList.size(); i++){

                    String currentText = arrayList.get(i).getCountry_name();
                    if (currentText.trim().toLowerCase().startsWith(search_text.trim().toLowerCase())){

                        arraySearchList.add(arrayList.get(i));

                    }


                }
                myLisAdapter = new MyLisAdapter(Countries.this, arraySearchList);
                listView.setAdapter(myLisAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





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

        String data = "";

        int countryNumber;

        public int getCountryNumber() {
            return countryNumber;
        }

        public void setCountryNumber(int countryNumber) {
            this.countryNumber = countryNumber;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                URL url = new URL("https://coronavirus-19-api.herokuapp.com/countries/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String lineData = "";


                while (lineData != null) {

                    lineData = bufferedReader.readLine();
                    data += lineData;

                }
                JSONArray jsonArray = new JSONArray(data);

                setCountryNumber(jsonArray.length());


                for (int i=1; i<numOfCountries; i++){


                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String countryName = jsonObject.getString("country");
                    String totalCases = String.valueOf(jsonObject.get("cases"));
                    String todayCases = String.valueOf(jsonObject.get("todayCases"));
                    String totalDeaths = String.valueOf(jsonObject.get("deaths"));
                    String todayDeaths = String.valueOf(jsonObject.get("todayDeaths"));
                    String totalRecovered = String.valueOf(jsonObject.get("recovered"));
                    String active_ = String.valueOf(jsonObject.get("active"));
                    String critical_ = String.valueOf(jsonObject.get("critical"));


                    country_name[i] = countryName;
                    total_cases[i] = totalCases;
                    deaths[i] = totalDeaths;
                    recovered[i] = totalRecovered;
                    today_cases[i] = todayCases;
                    today_deaths[i] = todayDeaths;
                    active[i] = active_;
                    critical[i] = critical_;


                    countryStat = new CountryStat();
                    countryStat.setCountry_name(country_name[i]);
                    countryStat.setTotal_cases(NumberFormat.getInstance(Locale.US).format(Integer.parseInt(total_cases[i])));
                    countryStat.setDeaths(NumberFormat.getInstance(Locale.US).format(Integer.parseInt(deaths[i])));
                    countryStat.setRecovered(recovered[i]);
                    countryStat.setToday_cases(today_cases[i]);
                    countryStat.setToday_deaths(today_deaths[i]);
                    countryStat.setActiveCondition(active[i]);
                    countryStat.setCriticalCondition(critical[i]);

                    countryStat.setCountry_num(i);

                    arrayList.add(countryStat);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }
}


class MyLisAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private ArrayList<CountryStat> arrayList;
    private ArrayList<CountryStat> privateArray;

    MyLisAdapter(Activity context, ArrayList<CountryStat> arrayList){
        super(context, R.layout.activity_mylist);

        this.context = context;
        this.arrayList = arrayList;

        privateArray = new ArrayList<CountryStat>();
        privateArray.addAll(arrayList);

    }



    public View getView(int pos, final View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_mylist, null, true);

        TextView countryNameTextView = rowView.findViewById(R.id.country_name);
        TextView totalCasesTextView = rowView.findViewById(R.id.total_cases);
        TextView deathsTextView = rowView.findViewById(R.id.deaths);
        TextView recoveriesTextView = rowView.findViewById(R.id.recoveries);
        TextView todayCasesTextView = rowView.findViewById(R.id.todayCases);
        TextView todayDeathsTextView = rowView.findViewById(R.id.todayDeaths);
        TextView activeTextView = rowView.findViewById(R.id.active);
        TextView criticalTextView = rowView.findViewById(R.id.critical);

        final LinearLayout minLinearLayout = rowView.findViewById(R.id.showMoreLayout);
        LinearLayout maxLinearLayout = rowView.findViewById(R.id.countryLayout);

        countryNameTextView.setText(arrayList.get(pos).getCountry_num() + ". " + arrayList.get(pos).getCountry_name());
        totalCasesTextView.setText("Total Cases: " + arrayList.get(pos).getTotal_cases());

        deathsTextView.setText("Total Deaths: " + arrayList.get(pos).getDeaths());
        recoveriesTextView.setText("Recovered: " + arrayList.get(pos).getRecovered());
        todayCasesTextView.setText("Today Cases: " + arrayList.get(pos).getToday_cases());
        todayDeathsTextView.setText("Today Deaths: " + arrayList.get(pos).getToday_deaths());
        activeTextView.setText("Good Condition: " + arrayList.get(pos).getActiveCondition());
        criticalTextView.setText("Critical Condition: " + arrayList.get(pos).getCriticalCondition());

        minLinearLayout.setVisibility(View.GONE);

        maxLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (minLinearLayout.getVisibility() == view.VISIBLE){
                    minLinearLayout.setVisibility(View.GONE);
                }else {
                    minLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });


        return rowView;



    }


    @Override
    public int getCount() {
        return arrayList.size();
    }



}

class CountryStat{

    private int country_num;
    private String country_name;
    private String total_cases;
    private String deaths;
    private String recovered;
    private String today_cases;
    private String today_deaths;
    private String activeCondition;
    private String criticalCondition;

    int getCountry_num() {
        return country_num;
    }

    void setCountry_num(int country_num) {
        this.country_num = country_num;
    }

    String getCountry_name() {
        return country_name;
    }

    void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    String getTotal_cases() {
        return total_cases;
    }

    void setTotal_cases(String total_cases) {
        this.total_cases = total_cases;
    }

    String getDeaths() {
        return deaths;
    }

    void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    String getRecovered() {
        return recovered;
    }

    void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    String getToday_cases() {
        return today_cases;
    }

    void setToday_cases(String today_cases) {
        this.today_cases = today_cases;
    }

    String getToday_deaths() {
        return today_deaths;
    }

    void setToday_deaths(String today_deaths) {
        this.today_deaths = today_deaths;
    }

    String getActiveCondition() {
        return activeCondition;
    }

    void setActiveCondition(String activeCondition) {
        this.activeCondition = activeCondition;
    }

    String getCriticalCondition() {
        return criticalCondition;
    }

    void setCriticalCondition(String criticalCondition) {
        this.criticalCondition = criticalCondition;
    }
}
