package com.example.Sameera.hearts.activity.fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.Sameera.hearts.R;
import com.example.Sameera.hearts.adapter.DoctorListAdapter;
import com.example.Sameera.hearts.app.MyApplication;
import com.example.Sameera.hearts.app.WebServiceURL;
import com.example.Sameera.hearts.model.Doctor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment {
    private TextView tvDate, tvTime;
    private Calendar mCurrentDate;
    private int day, month, year;
    private EditText edtDoctor, edtLocation;
    private Button btnAddSchedule, btnSave;
    private ListView listDoctor;
    DoctorListAdapter doctorListAdapter;
    ArrayList<Doctor> doctorArray = new ArrayList<Doctor>();
    private ProgressDialog pDialog;

    public DoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_doctor, container, false);
        edtDoctor = rootView.findViewById(R.id.edt_doctor_name);
        edtLocation = rootView.findViewById(R.id.edt_location);
        btnAddSchedule = rootView.findViewById(R.id.btn_add_doctor_schedule);
        btnSave = rootView.findViewById(R.id.btn_doctor_save);
        listDoctor = rootView.findViewById(R.id.list_doctor);

        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        tvDate = rootView.findViewById(R.id.tv_date);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        tvDate.setText("");

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        tvDate.setText(year + " / " + (month + 1) + " / " + dayOfMonth);
                    }
                }, year, month, day);
                dp.show();
            }
        });


        //  initiate the edit text
        tvTime = rootView.findViewById(R.id.time);
        // perform click event listener on edit text
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorArray.add(new Doctor(tvDate.getText().toString().trim(), tvTime.getText().toString().trim(), edtDoctor.getText().toString().trim(), edtLocation.getText().toString().trim()));
                doctorListAdapter = new DoctorListAdapter(getActivity(), doctorArray);
                listDoctor.setAdapter(doctorListAdapter);
                doctorListAdapter.notifyDataSetChanged();

                tvDate.setText("");
                tvTime.setText("");
                edtDoctor.setText("");
                edtLocation.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < doctorArray.size(); i++)
                {
                    saveSchedules(doctorArray.get(i).getDate(), doctorArray.get(i).getTime(), doctorArray.get(i).getName(), doctorArray.get(i).getLocation());
                }

            }
        });


        // Inflate the layout for this fragment
        return rootView;


    }

    private void saveSchedules(final String date, final String time, final String doctor, final String location) {
        // Tag used to cancel the request
        String tag_string_req = "req_doctor_schedules";

        pDialog.setMessage("Saving ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServiceURL.DOCTOR_SCHEDULE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Doctor Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean result = jObj.getBoolean("result");
                    if (result) {


                        Toast.makeText(getContext(), "Saved All Data !", Toast.LENGTH_LONG).show();

                    } else {

                        // Error occurred in registration. Get the result
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError result) {
                Log.e("TAG", "Doctor Error: " + result.getMessage());
                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", date);
                params.put("time", time);
                params.put("name", doctor);
                params.put("location", location);

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
