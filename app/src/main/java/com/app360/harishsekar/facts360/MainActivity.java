package com.app360.harishsekar.facts360;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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

    //FactsStore factsStore ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //factsStore = new FactsStore(this);

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






    private void setupViewPager(ViewPager viewPager){

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new home(),"Home");
        sectionsPagerAdapter.addFragment(new liked(),"Likes");
        sectionsPagerAdapter.addFragment(new feedback(),"Feedback");
        sectionsPagerAdapter.addFragment(new about(),"About");

        viewPager.setAdapter(sectionsPagerAdapter);

    }





}
