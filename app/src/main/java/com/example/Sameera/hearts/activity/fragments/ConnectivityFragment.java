package com.example.Sameera.hearts.activity.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.Sameera.hearts.R;
import com.example.Sameera.hearts.activity.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectivityFragment extends Fragment {

    private Button btnFinish;


    public ConnectivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_connectivity, container, false);


        btnFinish = rootView.findViewById(R.id.btn_finish);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeActivity = new Intent(getActivity(), HomeActivity.class);
                startActivity(homeActivity);
                getActivity().finish();
            }
        });


        // Inflate the layout for this fragment
        return rootView;


    }

}
