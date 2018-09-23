package com.example.Sameera.hearts.activity.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.Sameera.hearts.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddElderFragment extends Fragment {
    private TextView tvDOB;
    private Calendar mCurrentDate;
    private int day, month, year;


    public AddElderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_add_elder, container, false);

        tvDOB = rootView.findViewById(R.id.tv_dob);

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        tvDOB.setText("");

        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        tvDOB.setText(year + " / " + (month + 1) + " / " + dayOfMonth);
                    }
                }, year, month, day);
                dp.show();
            }
        });
        // Inflate the layout for this fragment
        return rootView;


    }


}
