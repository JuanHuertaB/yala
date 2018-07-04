package com.beater.yala.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.beater.yala.LoginActivity;
import com.beater.yala.R;
import com.beater.yala.data.SessionManagement;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView username, location,phoneNumber;
    private Button btnLogOut;
    SessionManagement session;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_profile, container, false);
        btnLogOut = view.findViewById(R.id.btn_logOut);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });
        session = new SessionManagement(getContext());
        loadPreferences(view);
        return view;
    }

    public void loadPreferences(View view) {
        HashMap<String, String> user = session.getUserDetails();

        username = (TextView) view.findViewById(R.id.username_profile);
        location = (TextView) view.findViewById(R.id.location_profile);
        phoneNumber = (TextView) view.findViewById(R.id.phonenumber_profile);

        username.setText(user.get(SessionManagement.KEY_NAME));
        location.setText(user.get(SessionManagement.KEY_LOCATION));
        phoneNumber.setText( user.get(SessionManagement.KEY_NUMBER));

    }
}
