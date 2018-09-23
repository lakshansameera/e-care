package com.example.Sameera.hearts.services;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.Sameera.hearts.util.AlarmReceiver;
import com.example.Sameera.hearts.util.SharedPref;

import java.util.Timer;
import java.util.TimerTask;


public class SpecialValuesService extends Service {

    private Timer timer;
    static boolean status = false;
    static int num = 0;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private SharedPref pref;
    //Pending intent instance
    private PendingIntent pendingIntent;
    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        timer = new Timer("Updater");
        timer.schedule(new TimerTask() {
            //
            @Override
            public void run() {
                pref = new SharedPref(getApplicationContext());

                /* Retrieve a PendingIntent that will perform a broadcast */
                Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_REQUEST_CODE, alarmIntent, 0);


                String[] values = pref.getValues();

                if (Integer.parseInt(values[0]) < 60 || Integer.parseInt(values[0]) > 100) {
                    Log.d("Values", "Please check your values");
                    triggerAlarmManager();

                } else if (Integer.parseInt(values[1]) < 120 || Integer.parseInt(values[1]) > 180) {
                    Log.d("Values", "Please check your values");
                    triggerAlarmManager();
                } else if (Integer.parseInt(values[2]) < 120 || Integer.parseInt(values[2]) > 180) {
                    Log.d("Values", "Please check your values");
                    triggerAlarmManager();
                }


            }

        }, 0, 60000);// within one minute

    }


    //Trigger alarm manager with entered time interval
    public void triggerAlarmManager() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);//set alarm manager with entered timer by converting into milliseconds


    }


    //Stop/Cancel alarm manager
    public void stopAlarmManager() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent


        //Stop the Media Player Service to stop sound
        stopService(new Intent(getApplicationContext(), AlarmSoundService.class));

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

    }

}
