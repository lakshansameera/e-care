package com.example.Sameera.hearts.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Sameera.hearts.R;
import com.example.Sameera.hearts.services.AlarmNotificationService;
import com.example.Sameera.hearts.services.AlarmSoundService;
import com.example.Sameera.hearts.services.SpecialValuesService;
import com.example.Sameera.hearts.util.SharedPref;
import com.example.Sameera.hearts.helper.SQLiteHandler;
import com.example.Sameera.hearts.helper.SessionManager;

public class HomeActivity extends AppCompatActivity {
    private final static int Elder = 1;
    private final static int Caregiver = 2;

    private SessionManager session;
    private SQLiteHandler db;
    private int userType;
    private SharedPref pref;
    private ImageView image_tempeture;
    private ImageView image_pressure;
    private ImageView image_rate;
    private TextView tvRate, tvTemp, tvPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("e-care");

        initialize();


        pref = new SharedPref(HomeActivity.this);
        userType = pref.getUserType();

        start_service();


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // session manager
        session = new SessionManager(getApplicationContext());


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent detailActivity = new Intent(HomeActivity.this, DetailActivity.class);
                startActivity(detailActivity);

            }
        });


        final Animation animation = new AlphaAnimation((float) 0.5, 0);
        animation.setDuration(800);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        image_pressure.startAnimation(animation);
        image_tempeture.startAnimation(animation);
        image_rate.startAnimation(animation);

        String special_values[] = pref.getValues();

        tvRate.setText("Rate : " + special_values[0]);
        tvTemp.setText("Temp : " + special_values[1]);
        tvPress.setText("Pressure : " + special_values[2]);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if (userType == Elder) {
            menu.getItem(0).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            logoutUser();
            return true;
        }
        if (id == R.id.action_reports) {

            Intent reportActivity = new Intent(HomeActivity.this, ReportActivity.class);
            startActivity(reportActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteCustomers();
        stop_service();

        // Launching the login activity
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void start_service() {
        Intent i = new Intent(HomeActivity.this, SpecialValuesService.class);
        startService(i);
    }

    public void stop_service() {
        Intent i = new Intent(HomeActivity.this, AlarmSoundService.class);
        stopService(i);
    }


    private void initialize() {
        image_pressure = (ImageView) findViewById(R.id.img_heart_preasure);
        image_tempeture = (ImageView) findViewById(R.id.img_heart_tempeture);
        image_rate = (ImageView) findViewById(R.id.img_heart_rate);

        tvRate = findViewById(R.id.tv_rate);
        tvTemp = findViewById(R.id.tv_temp);
        tvPress = findViewById(R.id.tv_press);


    }
}
