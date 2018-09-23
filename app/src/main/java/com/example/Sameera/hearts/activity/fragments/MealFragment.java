package com.example.Sameera.hearts.activity.fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.Sameera.hearts.R;
import com.example.Sameera.hearts.adapter.MealListAdapter;
import com.example.Sameera.hearts.app.MyApplication;
import com.example.Sameera.hearts.app.WebServiceURL;
import com.example.Sameera.hearts.model.Meal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealFragment extends Fragment {
    private TextView tvStartDate, tvEndDate;
    private EditText edtDescription;
    private Calendar mCurrentDate;
    private int day, month, year;
    private String mealType;
    private RadioButton rdoBreakfast, rdoLunch, rdoDinner;
    private Button btnAddSchedule, btnSave;
    private ListView listMeal;
    MealListAdapter mealListAdapter;
    ArrayList<Meal> mealArray = new ArrayList<Meal>();
    private ProgressDialog pDialog;

    public MealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_meal, container, false);
        tvStartDate = rootView.findViewById(R.id.tv_meal_start_date);
        tvEndDate = rootView.findViewById(R.id.tv_meal_end_date);
        rdoBreakfast = rootView.findViewById(R.id.rdoBreakfast);
        rdoLunch = rootView.findViewById(R.id.rdoLunch);
        rdoDinner = rootView.findViewById(R.id.rdoDinner);
        edtDescription = rootView.findViewById(R.id.edt_meal_description);
        btnAddSchedule = rootView.findViewById(R.id.btn_add_meal_schedule);
        btnSave = rootView.findViewById(R.id.btn_meal_save);
        listMeal = rootView.findViewById(R.id.list_meal);

        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        tvStartDate.setText("");
        tvEndDate.setText("");

        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        tvStartDate.setText(year + " / " + (month + 1) + " / " + dayOfMonth);
                    }
                }, year, month, day);
                dp.show();
            }
        });

        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        tvEndDate.setText(year + " / " + (month + 1) + " / " + dayOfMonth);
                    }
                }, year, month, day);
                dp.show();
            }
        });


        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdoBreakfast.isChecked()) {
                    mealType = "Breakfast";
                } else if (rdoLunch.isChecked()) {
                    mealType = "Lunch";
                } else if (rdoDinner.isChecked()) {
                    mealType = "Dinner";
                }


                mealArray.add(new Meal(tvStartDate.getText().toString().trim(), tvEndDate.getText().toString().trim(), mealType, edtDescription.getText().toString().trim()));
                mealListAdapter = new MealListAdapter(getActivity(), mealArray);
                listMeal.setAdapter(mealListAdapter);
                mealListAdapter.notifyDataSetChanged();

                tvStartDate.setText("");
                tvEndDate.setText("");
                edtDescription.setText("");

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mealArray.size(); i++) {
                    saveSchedules(mealArray.get(i).getSdate(), mealArray.get(i).getEdate(), mealArray.get(i).getType(), mealArray.get(i).getDesc());
                }
            }
        });
        // Inflate the layout for this fragment
        return rootView;


    }

    private void saveSchedules(final String startDate, final String endDate, final String type, final String description) {
        // Tag used to cancel the request
        String tag_string_req = "req_meal";

        pDialog.setMessage("Saving ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, WebServiceURL.MEAL_SCHEDULE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Meal Schedule Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean result = jObj.getBoolean("result");
                    if (result) {


                        Toast.makeText(getContext(), "Saved Meal Schedule !", Toast.LENGTH_LONG).show();

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
                Log.e("TAG", "Meal Schedule Error: " + result.getMessage());
                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("sdate", startDate);
                params.put("edate", endDate);
                params.put("type", type);
                params.put("description", description);

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
