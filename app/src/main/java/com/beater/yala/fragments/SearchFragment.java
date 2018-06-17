package com.beater.yala.fragments;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beater.yala.ContainerActivity;
import com.beater.yala.R;

import com.beater.yala.model.Album;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Struct;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private AsyncHttpClient cliente;
    private Spinner albumSpinner;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        showToolbar(getResources().getString(R.string.Buscar_Figuritas_toolbar_title),false, view);
        albumSpinner =  (Spinner) view.findViewById(R.id.spinner_albums);
        request = Volley.newRequestQueue(getContext());
        loadWebService();
        return view;
    }

    private void loadWebService() {
        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Integer idUser = preferences.getInt("id",1);

        String url = "https://juanhb.000webhostapp.com/Obtener_Albumes_By_Usuario.php?idUser="+ idUser +"" ;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);

    }

    private void llenaSpinner(){

        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Integer idUser = preferences.getInt("id",1);

        String url = "https://juanhb.000webhostapp.com/Obtener_Albumes_By_Usuario.php?idUser="+ idUser +"" ;

    }
    @Override
    public void onResponse(JSONObject response) {
        ArrayList<Album> lista = new ArrayList<Album>();
        JSONArray json = response.optJSONArray("albumes");
        JSONObject jsonObject;

        try {

            for(int i=0;i<json.length();i++) {
                jsonObject = json.getJSONObject(i);
                String nombreAlbum =jsonObject.optString("titulo");
                Album a = new Album();
                a.setAlbumName(nombreAlbum);
                lista.add(a);
                }

            ArrayAdapter<Album> a = new ArrayAdapter<Album>(getActivity().getApplicationContext(), R.layout.spinner_item,lista);
            albumSpinner.setAdapter(a);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void showToolbar(String title, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}
