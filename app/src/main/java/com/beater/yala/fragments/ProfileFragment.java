package com.beater.yala.fragments;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beater.yala.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView username, location,phoneNumber;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.fragment_profile, container, false);

        username = (TextView) view.findViewById(R.id.username_profile);
        location = (TextView) view.findViewById(R.id.location_profile);
        phoneNumber = (TextView) view.findViewById(R.id.phonenumber_profile);

        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

            String usernameSP= preferences.getString("username", "no encontrado");
            String locationSP = preferences.getString("location", "no encontrado");
            String phoneNumberSP = preferences.getString("phoneNumber", "no encontrado");

          username.setText(usernameSP);
          location.setText(locationSP);
          phoneNumber.setText(phoneNumberSP);

        return view;
    }


}
