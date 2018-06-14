package com.beater.yala.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.beater.yala.R;
import com.beater.yala.adapter.AlbumAdapterRecyclerView;
import com.beater.yala.dialogos.SolicitudAlbum;
import com.beater.yala.model.Album;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {


    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection,container,false);

        //showToolbar(getResources().getString(R.string.Coleccion_toolbar_title),false, view);

        RecyclerView albumsRecycler = (RecyclerView) view.findViewById(R.id.albumRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        albumsRecycler.setLayoutManager(linearLayoutManager);

        AlbumAdapterRecyclerView albumAdapterRecyclerView =
                new AlbumAdapterRecyclerView(buildAlbumes(), R.layout.cardview_album,getActivity());

        albumsRecycler.setAdapter(albumAdapterRecyclerView);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_id);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SolicitudAlbum.class);
                startActivity(intent);
            }
        });



        return view;

    }

    public ArrayList<Album> buildAlbumes(){
        ArrayList<Album> album = new ArrayList<>();
        album.add(new Album("https://i.imgur.com/rtf4wlj.jpg", "Dragon Ball Super", "Panini",600, 496, 54 ));
        album.add(new Album("https://i.imgur.com/JJiwCQF.jpg", "Dragon Ball Z", "Panini",600, 300, 54 ));
        album.add(new Album("https://i.imgur.com/3RSPN31.jpg", "Rusia 2018", "Panini",600, 300, 54 ));
        album.add(new Album("https://i.imgur.com/XBcoUKm.jpg", "Avengers: Infinity War", "Panini",600, 300, 54 ));
        return album;
    }

   public void showToolbar(String title, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
       ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
       ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
       ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}
