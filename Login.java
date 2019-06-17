package com.example.arshia.giftme;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import static com.example.shravya.project.R.layout.activity_listview;

public class Login extends AppCompatActivity {


    // private Toolbar toolbar2;
    android.support.v7.widget.Toolbar toolbar2;
    private android.support.design.widget.TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar2 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        VIewPagerAdapter adapter = new VIewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new ClientLogin(),"Client Login");
        adapter.addFragments(new AdminLogin(),"Admin Login");

        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);





    }
}