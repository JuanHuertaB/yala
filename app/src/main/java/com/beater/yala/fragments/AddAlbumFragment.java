package com.beater.yala.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.beater.yala.data.SessionManagement;
import com.beater.yala.dialogos.RequestAlbumDialog;
import com.beater.yala.model.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAlbumFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    ArrayList<Album> listaAlbumes;
    private RecyclerView addAlbumRecycler;
    private Button btn_solicitarAlbum;
    private Button btn_addAlbum;
    SessionManagement session;

    public AddAlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_album, container, false);
        session = new SessionManagement(getContext());
        listaAlbumes = new ArrayList<>();
        request = Volley.newRequestQueue(getContext());

        showToolbar(getResources().getString(R.string.Añadir_album_toolbar_title),true,view);

        //RECYCLERVIEW
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        addAlbumRecycler = (RecyclerView) view.findViewById(R.id.id_addAlbum);
        addAlbumRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));
        loadWebService();

        btn_solicitarAlbum = (Button) view.findViewById(R.id.btn_solicitar);
        btn_solicitarAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        return view;
    }

    private void openDialog() {
        RequestAlbumDialog requestAlbumDialog = new RequestAlbumDialog();
        requestAlbumDialog.show(getFragmentManager(),"Solicitar álbum ") ;
    }

    public void showToolbar(String title, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void loadWebService(){
        HashMap<String, String> user = session.getUserDetails();
        String idUser = user.get(SessionManagement.KEY_ID);

        String url = "https://juanhb.000webhostapp.com/Obtener_No_Albumes_By_Usuario.php?idUser="+ idUser +"" ;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Album album = null;
        listaAlbumes = new ArrayList<>();
        final AddAlbumAdapterRecyclerView adapter =
                new AddAlbumAdapterRecyclerView(listaAlbumes, R.layout.cardview_add_album, getActivity());

        if(response.optInt("estado") == 2){
            Snackbar.make(this.getView(),"No hay álbumes por agregar",Snackbar.LENGTH_SHORT).show();
        }else {

            JSONArray json = response.optJSONArray("albumes");
            try {
                for (int i = 0; i < json.length(); i++) {
                    album = new Album();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    album.setIdAlbum(jsonObject.optString("idAlbum"));
                    album.setPicture(jsonObject.optString("portada"));
                    album.setAlbumName(jsonObject.optString("titulo"));
                    album.setEditorial(jsonObject.optString("marca"));
                    album.setTotal(jsonObject.optInt("cantidad"));
                    listaAlbumes.add(album);
                }

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = listaAlbumes.get(addAlbumRecycler.getChildLayoutPosition(v)).getAlbumName();
                        Toast.makeText(getContext(), "Nombre álbum: " + name, Toast.LENGTH_SHORT).show();
                    }
                });

                addAlbumRecycler.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
