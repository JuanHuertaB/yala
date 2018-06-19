package com.beater.yala.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beater.yala.R;
import com.beater.yala.adapter.AddAlbumAdapterRecyclerView;
import com.beater.yala.dialogos.Solicitud_Dialogo;
import com.beater.yala.model.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAlbumFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private ArrayList listaAlbumes;
    private RecyclerView addAlbumRecycler;
    private Button btn_solicitarAlbum;
    Context contexto;

    public AddAlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_album, container, false);
        showToolbar(getResources().getString(R.string.Buscar_Figuritas_toolbar_title),true,view);
        listaAlbumes = new ArrayList<>();
        //RECYCLERVIEW
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addAlbumRecycler = (RecyclerView) view.findViewById(R.id.id_addAlbum);
        addAlbumRecycler.setLayoutManager(linearLayoutManager);
        request = Volley.newRequestQueue(getContext());
        loadWebService();
        btn_solicitarAlbum = (Button) view.findViewById(R.id.btn_solicitar);

        btn_solicitarAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    public void showToolbar(String title, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void loadWebService(){
        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Integer idUser = preferences.getInt("id",1);

        String url = "https://juanhb.000webhostapp.com/Obtener_No_Albumes_By_Usuario.php?idUser="+ idUser +"" ;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Album album = null;

        JSONArray json = response.optJSONArray("albumes");

        try {
            for(int i=0; i<json.length();i++){
                album = new Album();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                album.setPicture(jsonObject.optString("portada"));
                album.setAlbumName(jsonObject.optString("titulo"));
                album.setEditorial(jsonObject.optString("marca"));
                album.setTotal(jsonObject.optInt("cantidad"));
                listaAlbumes.add(album);

            }

            AddAlbumAdapterRecyclerView addAlbumAdapterRecyclerView =
                    new AddAlbumAdapterRecyclerView(listaAlbumes, R.layout.cardview_add_album,getActivity());
            addAlbumRecycler.setAdapter(addAlbumAdapterRecyclerView);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
