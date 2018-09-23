package com.example.Sameera.hearts.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.Sameera.hearts.R;
import com.example.Sameera.hearts.util.InternetObserver;
import com.example.Sameera.hearts.util.SharedPref;
import com.example.Sameera.hearts.app.WebServiceURL;
import com.example.Sameera.hearts.app.MyApplication;
import com.example.Sameera.hearts.helper.SQLiteHandler;
import com.example.Sameera.hearts.helper.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    private final static int Elder = 1;
    private final static int Caregiver = 2;

    private Button btnLogin;
    private CheckBox chkRemember;
    private TextView tvRegister;
    private TextInputEditText edtUsername, edtPassword;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private SessionManager session;
    private volatile boolean internetAvailability;
    private SharedPref pref;
    private String username, password;
    private boolean remember = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        initialize();

        pref = new SharedPref(LoginActivity.this);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            if (pref.getUserType() == Elder) {
                Intent intent = new Intent(LoginActivity.this, ConfigActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

        }


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                internetAvailability = InternetObserver.isConnectedToInternet(LoginActivity.this);
                if (internetAvailability) {
                    // user successfully logged in

                    username = edtUsername.getText().toString().trim();
                    password = edtPassword.getText().toString().trim();

                    if (chkRemember.isChecked()) {
                        remember = true;
                    }


                    checkLogin(username, password);


                } else {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Opz...")
                            .setContentText("No internet connection")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    setResult(RESULT_OK);


                                    sweetAlertDialog.cancel();
                                }
                            }).show();
                }


            }
        });
    }

    private void initialize() {
        btnLogin = findViewById(R.id.btnLogin);
        chkRemember = findViewById(R.id.chk_remember);
        tvRegister = findViewById(R.id.tv_register);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
    }


    private boolean validateUserame() {
        if (edtUsername.getText().toString().trim().isEmpty()) {
            edtUsername.setError(getString(R.string.err_msg_username));
            edtUsername.requestFocus();
            return false;

        }

        return true;
    }

    private boolean validatePassword() {
        if (edtPassword.getText().toString().trim().isEmpty()) {
            edtPassword.setError(getString(R.string.err_msg_password));
            edtPassword.requestFocus();
            return false;

        }

        return true;
    }

    private void checkLogin(final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServiceURL.LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean result = jObj.getBoolean("result");

                    // Check for result node in json
                    if (result) {
                        // user successfully logged in
                        // Create login session
                        if (remember) {
                            session.setLogin(true);
                        } else {
                            session.setLogin(false);
                        }

                        String type = jObj.getString("type");
                        if (type.equalsIgnoreCase("1")) {
                            pref.setUserType(1);
                            // Launch ConfigActivity
                            Intent intent = new Intent(LoginActivity.this, ConfigActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            pref.setUserType(2);
                            // Launch Home Activity
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    } else {
                        // Error in login. Get the result message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON result
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json result: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override   
            public void onErrorResponse(VolleyError result) {
                Log.e("TAG", "Login Error: " + result.getMessage());
                Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
