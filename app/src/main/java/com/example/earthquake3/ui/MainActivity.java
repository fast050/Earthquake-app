package com.example.earthquake3.ui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.earthquake3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    private boolean IsBackSuccess;
    private boolean IsDarkModeOn;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Saving state of our app
        // using SharedPreferences

        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        IsDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        // When user reopens the app


        bottomNavigationView = findViewById(R.id.bottomNavBar_main);

        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);



        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LastestEarthQuakeFragment()).commit();

    }

    private void DarkMod_Light()
    {
       // IsDarkModeOn;
        if (IsDarkModeOn) {

            // if dark mode is on it
            // will turn it off
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // it will set isDarkModeOn
            // boolean to false
            editor.putBoolean("isDarkModeOn", false);
            editor.apply();

            // change icon
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_light_mode));


        } else {

            // if dark mode is off
            // it will turn it on
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            // it will set isDarkModeOn
            // boolean to true
            editor.putBoolean("isDarkModeOn", true);
            editor.apply();

            // change icon
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_night_mode));
        }
        //keep the select home when we enter the app
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.home_nav)
                selectedFragment = LastestEarthQuakeFragment.newInstance();
            else if (item.getItemId() == R.id.search_nav)
            {
                selectedFragment = SearchFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .addToBackStack(null).commit();
            }
            else if (item.getItemId() == R.id.news_nav)
                selectedFragment = NewsFragment.newInstance();

            // It will help to replace the
            // one fragment to other.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {

        IsBackSuccess =false;

        new AlertDialog.Builder(this)
                 //set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
                 //set title
                .setTitle("Are you sure to Exit ?")
                 //set message
                 //Exiting will call finish() method
                .setMessage("Press Yes If You Want to Exit This App , No IF You Don't ")
                //set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        IsBackSuccess=true;
                        finish();
                    }
                })
                 //set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Toast.makeText(getApplicationContext(),"Welcome Back",Toast.LENGTH_SHORT).show();
                        IsBackSuccess =false;
                    }
                }).show();


         if(IsBackSuccess)
        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        this.menu = menu;
        if (IsDarkModeOn)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_night_mode));

        } else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_light_mode));

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.mode_item) {

            DarkMod_Light();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

