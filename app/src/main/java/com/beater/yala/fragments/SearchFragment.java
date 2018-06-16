package com.beater.yala.fragments;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.beater.yala.R;

import com.beater.yala.model.Album;
import com.loopj.android.http.*;

import org.json.JSONArray;

import java.sql.Struct;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private AsyncHttpClient cliente;
    private Spinner albumSpinner;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        cliente = new AsyncHttpClient();
        Spinner spinner =  (Spinner) view.findViewById(R.id.spinner_albums);

        return view;
    }

    private void llenaSpinner(){

        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        Integer idUser = preferences.getInt("id",1);

        String url = "https://juanhb.000webhostapp.com/Obtener_Albumes_By_Usuario.php?idUser="+ idUser +"" ;

        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode== 200){
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void cargarSpinner(String respuesta) {
        ArrayList<Album> lista = new ArrayList<Album>();

        try{
            JSONArray json = new JSONArray(respuesta);
            for (int i = 0 ; i < respuesta.length(); i++){
                Album a = new Album();
            }

        }catch(Exception e){

        }

    }

}
