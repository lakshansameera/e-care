package com.example.Sameera.hearts.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.example.Sameera.hearts.R;
import com.example.Sameera.hearts.activity.fragments.DoctorFragment;
import com.example.Sameera.hearts.activity.fragments.MealFragment;
import com.example.Sameera.hearts.activity.fragments.PrescriptionFragment;
import com.example.Sameera.hearts.activity.fragments.SpecialFragment;

public class DetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    CustomerDetailsPagerAdapter pageAdapter;
    private DoctorFragment doctorFragment;
    private MealFragment mealFragment;
    private SpecialFragment specialFragment;
    private PrescriptionFragment prescriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("Details");

        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.customer_details_tab_strip);
        pageAdapter = new CustomerDetailsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.customer_details_viewpager);
        viewPager.setAdapter(pageAdapter);

        slidingTabStrip.setViewPager(viewPager);
        slidingTabStrip.setIndicatorColor(ContextCompat.getColor(DetailActivity.this, R.color.colorPrimary));

    }


    //    adapter for tab view
    private class CustomerDetailsPagerAdapter extends FragmentPagerAdapter {

        private final String[] titles = {"Doctor Meetings", "Meals", "Special", "Prescriptions"};

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
                    if (doctorFragment == null)
                        doctorFragment = new DoctorFragment();
                    return doctorFragment;
                case 1:
                    if (mealFragment == null)
                        mealFragment = new MealFragment();
                    return mealFragment;

                case 2:
                    if (specialFragment == null)
                        specialFragment = new SpecialFragment();
                    return specialFragment;

                case 3:
                    if (prescriptionFragment == null)
                        prescriptionFragment = new PrescriptionFragment();
                    return prescriptionFragment;


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
