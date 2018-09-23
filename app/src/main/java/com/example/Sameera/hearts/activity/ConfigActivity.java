package com.example.Sameera.hearts.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.example.Sameera.hearts.R;
import com.example.Sameera.hearts.activity.fragments.AddElderFragment;
import com.example.Sameera.hearts.activity.fragments.AddCaregiverFragment;
import com.example.Sameera.hearts.activity.fragments.ConnectivityFragment;

public class ConfigActivity extends AppCompatActivity {

    private ViewPager viewPager;
    CustomerDetailsPagerAdapter pageAdapter;
    private AddElderFragment addElderFragment;
    private AddCaregiverFragment addCaregiverFragment;
    private ConnectivityFragment connectivityFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        setTitle("Config");

        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.customer_details_tab_strip);
        pageAdapter = new CustomerDetailsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.customer_details_viewpager);
        viewPager.setAdapter(pageAdapter);


        slidingTabStrip.setViewPager(viewPager);
        slidingTabStrip.setIndicatorColor(ContextCompat.getColor(ConfigActivity.this, R.color.colorPrimary));

    }


    //    adapter for tab view
    private class CustomerDetailsPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"1", "2", "3"};

        public CustomerDetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (addElderFragment == null)
                        addElderFragment = new AddElderFragment();
                    return addElderFragment;
                case 1:
                    if (addCaregiverFragment == null)
                        addCaregiverFragment = new AddCaregiverFragment();
                    return addCaregiverFragment;

                case 2:
                    if (connectivityFragment == null)
                        connectivityFragment = new ConnectivityFragment();
                    return connectivityFragment;


                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }

    }


}
