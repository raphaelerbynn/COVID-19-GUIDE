package com.mountain.covid19guide;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    static Intent intent;

    BottomNavigationView bottomNavigationView;
    NestedScrollView nestedScrollView;

    BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()){
                case R.id.home:

                    fragment = new HomeFragment();

                    break;

                case R.id.hotlines:

                    fragment = new HotlineFragment();

                    break;


                case R.id.about:

                    fragment = new AboutFragment();

                    break;

                default:
                    return false;



            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;

        }
    };



    public void click(View view)  {

        if (view.getId() == R.id.countriesCardView){
            intent = new Intent(getApplicationContext(), Countries.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.symptomsCardView){
            intent = new Intent(getApplicationContext(), Symptoms.class);
            startActivity(intent);


        }
        else if (view.getId() == R.id.precautionCardView){
            intent = new Intent(getApplicationContext(), Prevention.class);
            startActivity(intent);

            Log.i("ClickSymptom", "Symptoms clicked" );
        }
        else if (view.getId() == R.id.articlesCardView){
            intent = new Intent(getApplicationContext(), Articles.class);
            startActivity(intent);
        }
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



        nestedScrollView = findViewById(R.id.nestedScrollView);


        bottomNavigationView = findViewById(R.id.buttomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();








//        Log.i("bottomvav", String.valueOf(bottomNavigationView.getHeight()));

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setMargins(0, 0,0,0);
        bottomNavigationView.setLayoutParams(layoutParams);
//
        layoutParams.setBehavior(new BottomNavBehavior());





    }


//    protected boolean internetIsAvailable() {
//
//        boolean have_Wifi = false;
//        boolean have_MobileData = false;
//
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        assert connectivityManager != null;
//        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
//
//        for (NetworkInfo info : networkInfos) {
//
//            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
//                if (info.isConnected()) {
//                    have_Wifi = true;
//                }
//            }
//            if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
//                if (info.isConnected()) {
//                    have_MobileData = true;
//                }
//            }
//
//        }
//
//        return have_Wifi || have_MobileData;
//
//    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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



    public void alertDialogue(final String number){

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.sym_action_call)
                .setTitle("Call Emergency line")
                .setMessage("Do you really want to call " + number)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + number));

                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 0);

                        }
                        else {
                            startActivity(intent);
                        }

                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    public void call(View view){
        int id = view.getId();
        final String ends700 = (String) HotlineFragment.call700.getText();
        final String ends004 = (String) HotlineFragment.call004.getText();
        final String ends005 = (String) HotlineFragment.call005.getText();
        final String ends868 = (String) HotlineFragment.call868.getText();
        final String ends311 = (String) HotlineFragment.call311.getText();

        switch (id){
            case R.id.button700:
                alertDialogue(ends700);
                break;
            case R.id.button004:
                alertDialogue(ends004);
                break;
            case R.id.button005:
                alertDialogue(ends005);
                break;
            case R.id.button868:
                alertDialogue(ends868);
                break;
            case R.id.button311:
                alertDialogue(ends311);
                break;
            default:
                break;
        }
    }

}
