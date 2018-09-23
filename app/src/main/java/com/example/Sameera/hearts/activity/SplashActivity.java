package com.example.Sameera.hearts.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.Sameera.hearts.R;
import com.example.Sameera.hearts.helper.SessionManager;

public class SplashActivity extends Activity {
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (isPermissionGranted()) {

            initialize();


        }


    }

    private void initialize() {

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // session manager
                session = new SessionManager(getApplicationContext());

                if (session.isLoggedIn()) {
                    Intent login = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(login);
                    finish();

                } else {
                    Intent login = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();

                }


            }
        }.start();

    }

    private boolean isPermissionGranted() {

        {
            if (Build.VERSION.SDK_INT >= 23) {
                if (
                        (checkSelfPermission(Manifest.permission.VIBRATE
                        )
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.INTERNET)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.READ_SMS)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.BLUETOOTH)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.GET_ACCOUNTS)
                                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.WAKE_LOCK)
                                == PackageManager.PERMISSION_GRANTED)) {
                    Log.i("v", "Permission is granted");
                    return true;
                } else {

                    Log.i("v", "Permission is revoked");
                    ActivityCompat.requestPermissions(this, new String[]
                            {
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN,
                            Manifest.permission.VIBRATE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.INTERNET,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.GET_ACCOUNTS}
                            , 1);
                    return false;
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.i("v", "Permission is granted");
                return true;
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean allow_go = true;

        for (int i = 0; i < permissions.length; i++) {
            Log.e("xx", permissions[i] + " " + grantResults[i]);

            if (grantResults[i] == -1) {
                allow_go = false;
            }
        }


        if (allow_go) {

            initialize();
        } else {
            Toast.makeText(SplashActivity.this, "Provide All Permissions", Toast.LENGTH_SHORT).show();
            isPermissionGranted();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }
}
