package com.beater.yala.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.beater.yala.R;
import com.beater.yala.adapter.FiguresAdapterRecyclerView;
import com.beater.yala.model.Album;
import com.beater.yala.model.Figure;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDetailsFragment extends Fragment {
    private RecyclerView figurasRecycler;
    ArrayList<Figure> listaFiguras;
    TextView nameAlbum,editorial,total,conseguidas,faltantes;
    Button btnRepetidas;
    private RepetidasFragment repetidasFragment;


    public AlbumDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_details, container, false);
        nameAlbum = (TextView) view.findViewById(R.id.tituloAlbum);
        editorial= (TextView) view.findViewById(R.id.editorialAlbum);
        total = (TextView) view.findViewById(R.id.totalAlbum);
        conseguidas= (TextView) view.findViewById(R.id.conseguidasAlbum);
        faltantes= (TextView) view.findViewById(R.id.faltantesAlbum);
        btnRepetidas = (Button) view.findViewById(R.id.btnRepetidas);

        repetidasFragment = new RepetidasFragment();
        btnRepetidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame,repetidasFragment);
                transaction.commit();
            }
        });



        listaFiguras = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        figurasRecycler = (RecyclerView) view.findViewById(R.id.Recycler_figura);
        figurasRecycler.setLayoutManager(new GridLayoutManager(getContext(),6));

        Bundle datosAlbum = getArguments();
        Album album = null;

        if(datosAlbum != null){
            album = (Album) datosAlbum.getSerializable("album");
            nameAlbum.setText(album.getAlbumName());
            editorial.setText(album.getEditorial());
            total.setText(Integer.toString(album.getTotal()));
            conseguidas.setText(Integer.toString(album.getCompletadas()));
            faltantes.setText(Integer.toString(album.getTotal()- album.getCompletadas()));

            loadFigures(view, album.getTotal());

        }


        return view;
    }

    private void loadFigures(View view, int cantFiguras) {
        Figure figure = null;
        listaFiguras = new ArrayList<>();

        final FiguresAdapterRecyclerView adapter = new FiguresAdapterRecyclerView(listaFiguras,R.layout.cardview_figure,getActivity());

        if (cantFiguras > 0){
            for(int i = 1; i<=cantFiguras; i++){
                figure = new Figure();
                figure.setNumber(i);
                listaFiguras.add(figure);
            }

            figurasRecycler.setAdapter(adapter);
        }

    }

}
