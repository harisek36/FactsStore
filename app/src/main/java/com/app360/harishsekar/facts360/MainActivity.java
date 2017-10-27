package com.app360.harishsekar.facts360;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager mviewPager;

    AlarmManager  alarmManager;
    Intent alarm_intent;
    Calendar calendar;

    FactsStore factsStore ;
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        factsStore = new FactsStore(this);

        SharedPreferences editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        int cat_pos = editor.getInt("current_Category_position",0);
        String file = editor.getString("current_category","ALL");
//        int fact_pos = editor.getInt("current_index", 0);

        int all = editor.getInt("ALL", 0);
        int interesting =editor.getInt("INTERESTING", 0);
        int humanbody =editor.getInt("HUMANBODY", 0);
        int nature =editor.getInt("NATURE", 0);
        int science =editor.getInt("SCIENCE", 0);
        int statistics =editor.getInt("STATISTICS", 0);
        int worldrecords =editor.getInt("WORLDRECORDS", 0);
        int famousquotes =editor.getInt("FAMOUSQUOTES", 0);

        factsStore.setFileName(file);
        factsStore.setCategory_position(cat_pos);
//        factsStore.setAll_Facts_index_position(cat_pos,fact_pos);
        factsStore.setAll_Facts_index_position(0,all);
        factsStore.setAll_Facts_index_position(1,interesting);
        factsStore.setAll_Facts_index_position(2,humanbody);
        factsStore.setAll_Facts_index_position(3,nature);
        factsStore.setAll_Facts_index_position(4,science);
        factsStore.setAll_Facts_index_position(5,statistics);
        factsStore.setAll_Facts_index_position(6,worldrecords);
        factsStore.setAll_Facts_index_position(7,famousquotes);




        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //setting up view pagger with Sections adaptor

        mviewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mviewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_intent = new Intent(getApplicationContext(),AlarmReceiver.class);
        calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY,18);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,00);
        PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(),100,alarm_intent,PendingIntent.FLAG_UPDATE_CURRENT);;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);







    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("current_category",factsStore.getFileName());
        editor.putInt("current_Category_position", factsStore.getCategory_position());
        editor.putInt("ALL", factsStore.getAll_Facts_index_position(0));
        editor.putInt("INTERESTING", factsStore.getAll_Facts_index_position(1));
        editor.putInt("HUMANBODY", factsStore.getAll_Facts_index_position(2));
        editor.putInt("NATURE", factsStore.getAll_Facts_index_position(3));
        editor.putInt("SCIENCE", factsStore.getAll_Facts_index_position(4));
        editor.putInt("STATISTICS", factsStore.getAll_Facts_index_position(5));
        editor.putInt("WORLDRECORDS", factsStore.getAll_Facts_index_position(6));
        editor.putInt("FAMOUSQUOTES", factsStore.getAll_Facts_index_position(7));





        editor.commit();


    }






    private void setupViewPager(ViewPager viewPager){

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new home(),"Home");
        sectionsPagerAdapter.addFragment(new liked(),"Likes");
        sectionsPagerAdapter.addFragment(new feedback(),"Feedback");
        sectionsPagerAdapter.addFragment(new about(),"About");

        viewPager.setAdapter(sectionsPagerAdapter);

    }





}
