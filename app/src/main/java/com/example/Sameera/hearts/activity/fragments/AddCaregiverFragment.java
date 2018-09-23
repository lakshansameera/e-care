package com.example.Sameera.hearts.activity.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.Sameera.hearts.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCaregiverFragment extends Fragment {
    private ListView listCaregiver;


    public AddCaregiverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_add_caregiver, container, false);

        listCaregiver = rootView.findViewById(R.id.list_caregiver);

        String listItemArray[] = {"Caregiver 1", "Caregiver 2", "Caregiver 3",
                "Caregiver 4", "Caregiver 5", "Caregiver 6", "Caregiver 7"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItemArray);

        listCaregiver.setAdapter(adapter);


        // Inflate the layout for this fragment
        return rootView;


    }

}
