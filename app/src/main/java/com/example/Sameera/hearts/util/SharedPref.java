package com.example.Sameera.hearts.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static final String LOG_TAG = SharedPref.class.getSimpleName();

    private Context context;
    private SharedPreferences pref;
    private static SharedPref sharedPref;

    public static SharedPref getInstance(Context context) {
        if (sharedPref == null) {
            sharedPref = new SharedPref(context);
        }

        return sharedPref;
    }


    public SharedPref(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);

    }

    /*get special values*/
    public String[] getValues() {
        return new String[]{
                pref.getString("rate", "67"),
                pref.getString("temp", "130"),
                pref.getString("pres", "130")
        };
    }

    // save uname and pword
    public void setAuthentication(String Uname, String pword) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("uname", Uname);
        editor.putString("pword", pword);
        editor.commit();
    }

    public String[] getAuthentication() {
        return new String[]{pref.getString("uname", ""),
                pref.getString("pword", "")};
    }


    public void clearAuthentication() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("uname", "");
        editor.putString("pword", "");
        editor.commit();
    }


    /*Set User type*/
    public void setUserType(int userType) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("USER_TYPE", userType);
        editor.commit();
    }

    /*Retrieve User type*/
    public int getUserType() {
        return pref.getInt("USER_TYPE", 0);
    }

    /*Clear User type*/
    public void clearUserType() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("USER_TYPE", 0);
        editor.commit();
    }


    public void clear_shared_pref() {

        pref.edit().clear().commit();

    }


}
