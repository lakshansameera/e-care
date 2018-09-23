package com.example.Sameera.hearts.activity.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.Sameera.hearts.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrescriptionFragment extends Fragment {
    private Spinner spinner_hours;


    public PrescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_prescription, container, false);

        spinner_hours = rootView.findViewById(R.id.spinner_hours);

        ArrayList spinner_list = new ArrayList();

        spinner_list.add("0");
        spinner_list.add("1");
        spinner_list.add("2");
        spinner_list.add("3");
        spinner_list.add("4");
        spinner_list.add("5");
        spinner_list.add("6");
        spinner_list.add("7");
        spinner_list.add("8");
        spinner_list.add("9");
        spinner_list.add("10");
        spinner_list.add("11");
        spinner_list.add("12");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, spinner_list);

        spinner_hours.setAdapter(arrayAdapter);

        // Inflate the layout for this fragment
        return rootView;


    }

}
