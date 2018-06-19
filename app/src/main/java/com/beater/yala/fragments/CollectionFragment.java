package com.beater.yala.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beater.yala.R;
import com.beater.yala.adapter.AlbumAdapterRecyclerView;
import com.beater.yala.model.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private AddAlbumFragment addAlbumFragment;
    private FrameLayout frameLayout;
    private RecyclerView albumsRecycler;
    private ProgressDialog progress;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue request;
    private ArrayList listaAlbumes;


    public CollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection,container,false);
        frameLayout = (FrameLayout) container.findViewById(R.id.main_frame);

        addAlbumFragment = new AddAlbumFragment();
        listaAlbumes = new ArrayList<>();
        request = Volley.newRequestQueue(getContext());

        //TOOLBAR
        showToolbar(getResources().getString(R.string.Coleccion_toolbar_title),true, view);

        //RECYCLERVIEW
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        albumsRecycler = (RecyclerView) view.findViewById(R.id.albumRecycler);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        albumsRecycler.setLayoutManager(linearLayoutManager);

        //FLOATING ACTION BUTTON
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_id);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, addAlbumFragment);
                fragmentTransaction.commit();

            }
        });
        loadWebService();
        return view;
    }

/*    public ArrayList<Album> buildAlbumes(){
        ArrayList<Album> album = new ArrayList<>();
        album.add(new Album("https://i.imgur.com/rtf4wlj.jpg", "Dragon Ball Super", "Panini",600, 496, 54 ));
        album.add(new Album("https://i.imgur.com/JJiwCQF.jpg", "Dragon Ball Z", "Panini",600, 300, 54 ));
        album.add(new Album("https://i.imgur.com/3RSPN31.jpg", "Rusia 2018", "Panini",600, 300, 54 ));
        album.add(new Album("https://i.imgur.com/XBcoUKm.jpg", "Avengers: Infinity War", "Panini",600, 300, 54 ));
        return album;
    }*/

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

        String url = "https://juanhb.000webhostapp.com/Obtener_Albumes_By_Usuario.php?idUser="+ idUser +"" ;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onResponse(JSONObject response) {
        Album album = null;

        JSONArray json =  response.optJSONArray("albumes");

        try {

            for(int i = 0; i<json.length();i++){
                album = new Album();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                album.setAlbumName(jsonObject.optString("titulo"));
                album.setEditorial(jsonObject.optString("marca"));
                album.setPicture(jsonObject.optString("portada"));
                album.setTotal(jsonObject.optInt("cantidad"));
                album.setCompletadas(jsonObject.optInt("conseguidas"));
                album.setRepetidas(jsonObject.optInt("repetidas"));
                listaAlbumes.add(album);
            }

            AlbumAdapterRecyclerView albumAdapterRecyclerView =
                    new AlbumAdapterRecyclerView(listaAlbumes, R.layout.cardview_album,getActivity());
            albumsRecycler.setAdapter(albumAdapterRecyclerView);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
