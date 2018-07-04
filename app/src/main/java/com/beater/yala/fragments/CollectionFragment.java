package com.beater.yala.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beater.yala.R;
import com.beater.yala.adapter.AlbumAdapterRecyclerView;
import com.beater.yala.data.SessionManagement;
import com.beater.yala.interfaces.IComunicaFragments;
import com.beater.yala.model.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private AddAlbumFragment addAlbumFragment;
    private AlbumDetailsFragment albumFragment;
    private FrameLayout frameLayout;
    private RecyclerView albumsRecycler;
    private ProgressDialog progress;
    private ArrayList<Album> listaAlbumes;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    SessionManagement session;
    Activity activity;
    IComunicaFragments icFragments;
    ImageView pictureAlbum;

    public CollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection,container,false);
        frameLayout = (FrameLayout) container.findViewById(R.id.main_frame);
        progress = new ProgressDialog(getContext());
        albumFragment = new AlbumDetailsFragment();
        addAlbumFragment = new AddAlbumFragment();
        listaAlbumes = new ArrayList<Album>();
        request = Volley.newRequestQueue(getContext());

        session = new SessionManagement(getContext());

        //TOOLBAR
        showToolbar(getResources().getString(R.string.Coleccion_toolbar_title),false, view);

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

        session.checkLogin();
        loadWebService();
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
        showProgressDialog("Cargando Colección");
        HashMap<String, String> user = session.getUserDetails();
        String idUser = user.get(SessionManagement.KEY_ID);

        String url = "https://juanhb.000webhostapp.com/Obtener_Albumes_By_Usuario.php?idUser="+ idUser +"" ;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        Album album = null;
        final AlbumAdapterRecyclerView adapter = new AlbumAdapterRecyclerView(listaAlbumes, R.layout.cardview_album, getActivity());

        if (response.optInt("estado") == 1) {

            JSONArray json = response.optJSONArray("albumes");

            try {

                for (int i = 0; i < json.length(); i++) {
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

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        icFragments.enviarAlbum(listaAlbumes.get(albumsRecycler.getChildLayoutPosition(v)));
                    }
                });

                albumsRecycler.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(response.optInt("estado") == 2){
            Snackbar.make(this.getView(),"No tiene álbumes en su colección",Snackbar.LENGTH_SHORT).show();
        }
        progress.hide();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void showProgressDialog(String title) {
        progress.setMessage(title);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgressStyle(TextPaint.LINEAR_TEXT_FLAG);
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.activity = (Activity) context;
            icFragments = (IComunicaFragments) this.activity;
        }
    }
}
