package com.example.Sameera.hearts.util;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.Sameera.hearts.services.AlarmNotificationService;
import com.example.Sameera.hearts.services.AlarmSoundService;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/*
 * Created by Sameera on 10/07/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Stop sound service to play sound for alarm
        context.startService(new Intent(context, AlarmSoundService.class));

        //This will send a notification message and show notification in notification tray
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmNotificationService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));

    }


}
