package com.beater.yala.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.beater.yala.data.SessionManagement;
import com.beater.yala.model.Album;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private AsyncHttpClient cliente;
    private Spinner albumSpinner;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    SessionManagement session;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        session = new SessionManagement(getContext());

        //TOOLBAR
        showToolbar(getResources().getString(R.string.Buscar_Figuritas_toolbar_title),false, view);

        //SPINNER
        albumSpinner =  (Spinner) view.findViewById(R.id.spinner_albums);
        request = Volley.newRequestQueue(getContext());
        //REALIZA LA CONSULTA A BD
        loadWebService();
        return view;
    }

    private void loadWebService() {
        HashMap<String,String> user = session.getUserDetails();
        String idUser = user.get(SessionManagement.KEY_ID);

        String url = "https://juanhb.000webhostapp.com/Obtener_Albumes_By_Usuario.php?idUser="+ idUser +"" ;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<Album> lista = new ArrayList<>();

        if (response.optInt("estado") == 1) {

            JSONArray json = response.optJSONArray("albumes");
            JSONObject jsonObject;
            ArrayAdapter<Album> a = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, lista);

            try {

                for (int i = 0; i < json.length(); i++) {
                    jsonObject = json.getJSONObject(i);
                    String nombreAlbum = jsonObject.optString("titulo");
                    Album album = new Album();
                    album.setAlbumName(nombreAlbum);
                    lista.add(album);
                }


                albumSpinner.setAdapter(a);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(response.optInt("estado") == 2){
            Snackbar.make(this.getView(),"agregue álbumes a su colección para buscar",Snackbar.LENGTH_SHORT).show();
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
