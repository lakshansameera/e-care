package com.example.Sameera.hearts.activity.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.Sameera.hearts.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialFragment extends Fragment {
    private Spinner spinnerMinutes;
    private TextView tvStartDate, tvEndDate;
    private Calendar mCurrentDate;
    private int day, month, year;

    public SpecialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_special, container, false);

        spinnerMinutes = rootView.findViewById(R.id.spinner_minutes);


        ArrayList spinner_list = new ArrayList();
        spinner_list.add("0");
        spinner_list.add("5");
        spinner_list.add("10");
        spinner_list.add("15");
        spinner_list.add("20");
        spinner_list.add("25");
        spinner_list.add("30");
        spinner_list.add("35");
        spinner_list.add("40");
        spinner_list.add("45");
        spinner_list.add("50");
        spinner_list.add("55");
        spinner_list.add("60");


        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, spinner_list);

        spinnerMinutes.setAdapter(arrayAdapter);

        tvStartDate = rootView.findViewById(R.id.tv_special_start_date);
        tvEndDate = rootView.findViewById(R.id.tv_special_end_date);

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
        // Inflate the layout for this fragment
        return rootView;


    }

}
