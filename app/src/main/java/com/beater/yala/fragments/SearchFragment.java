package com.beater.yala.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beater.yala.R;

import com.beater.yala.adapter.SearchUsersAdapterRecyclerView;
import com.beater.yala.data.JSONParser;
import com.beater.yala.data.SessionManagement;
import com.beater.yala.dialogos.AlertDialogManager;
import com.beater.yala.model.Album;
import com.beater.yala.model.Coleccionista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String TAG = "SearchFragment";
    private static final String TAG_SUCCESS = "estado";
    private static final String TAG_USER = "users";
    final HashMap<String,String> albumDetails = new HashMap<String, String>();
    private final String URL_SEARCH = "https://juanhb.000webhostapp.com/Buscar_Figuritas.php";
    private ArrayList<Coleccionista> coleccionistasLista;
    private Spinner albumSpinner;
    public Album album;
    public EditText numberField;
    private Button btnSearch;
    private ProgressDialog progress;
    JSONParser jsonParser = new JSONParser();
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    SessionManagement session;
    AlertDialogManager alertDialogManager;
    String number;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        session = new SessionManagement(getContext());
        progress = new ProgressDialog(getContext());
        alertDialogManager = new AlertDialogManager();
        coleccionistasLista = new ArrayList<Coleccionista>();
        final String hostname = getString(R.string.host_name);

        //TOOLBAR
        showToolbar(getResources().getString(R.string.Buscar_Figuritas_toolbar_title),false, view);

        //SPINNER
        albumSpinner =  (Spinner) view.findViewById(R.id.spinner_albums);
        request = Volley.newRequestQueue(getContext());

        //REALIZA LA CONSULTA A BD PARA EL SPINNER
        loadSpinner(hostname);
        btnSearch = (Button)view.findViewById(R.id.btnSearch);
        numberField = (EditText) view.findViewById(R.id.numberFigure);
        return view;
    }

    private void loadSpinner(String hostname) {
        //showProgressDialog("Cargando Colección");
        HashMap<String,String> user = session.getUserDetails();
        String idUser = user.get(SessionManagement.KEY_ID);

        String url = hostname + "/Obtener_Albumes_By_Usuario.php?idUser="+ idUser +"" ;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        final ArrayList<Album> lista = new ArrayList<>();

        if (response.optInt("estado") == 1) {

            JSONArray json = response.optJSONArray("albumes");
            JSONObject jsonObject;
            ArrayAdapter<Album> a = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, lista);

            try {
                album = new Album();
                for (int i = 0; i < json.length(); i++) {
                    album = new Album();
                    jsonObject = json.getJSONObject(i);

                    String nombreAlbum = jsonObject.optString("titulo");
                    String idAlbum = jsonObject.optString("idAlbum");
                    albumDetails.put(nombreAlbum,idAlbum);//ASOCIA EL ID AL NOMBRE DEL ÁLBUM
                    album.setIdAlbum(idAlbum);
                    album.setAlbumName(nombreAlbum);
                    lista.add(album);
                }
                    albumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                        btnSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                number = numberField.getText().toString();
                                String idAlbum= albumDetails.get(parent.getItemAtPosition(position).toString());
                                new searchFigures().execute(number,idAlbum);
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                    albumSpinner.setAdapter(a);
                     progress.hide();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(response.optInt("estado") == 2){
            Snackbar.make(this.getView(),"agregue álbumes a su colección para buscar",Snackbar.LENGTH_SHORT).show();
        }
        progress.hide();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private class searchFigures extends AsyncTask<String, Void, Void>{
        private String content;
        int success;
        JSONObject json = null;
        Coleccionista coleccionista = null;

        RecyclerView usersRecycler = (RecyclerView) getActivity().findViewById(R.id.usersRecycler);

        @Override
        protected void onPreExecute() {
            Log.d(TAG,"On post Execute: starts");
            //alertDialogManager.showAlertDialog(getContext(),"Buscando ...","Espere por favor",true);
        }

        @Override
        protected Void doInBackground(final String... args) {
            Log.d(TAG,"On Do in Background: starts");
            try {
                String numFigure = args[0];
                String idAlbum = args[1];

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("number", numFigure));
                params.add(new BasicNameValuePair("idAlbum", idAlbum));

                json = jsonParser.makeHttpRequest(URL_SEARCH, "GET", params);
                Log.d("Search Details", json.toString());

                success = json.optInt(TAG_SUCCESS);

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG,"On post Execute: starts");

            if (success == 1) {
                JSONArray jsonResponse = json.optJSONArray(TAG_USER);

                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject jsonObject = jsonResponse.optJSONObject(i);
                    coleccionista = new Coleccionista();
                    coleccionista.setId(jsonObject.optString("idColeccionista"));
                    coleccionista.setUsername(jsonObject.optString("usuario"));
                    coleccionista.setPhoneNumber(jsonObject.optString("contacto"));
                    coleccionista.setLocation(jsonObject.optString("ubicacion"));
                    coleccionistasLista.add(coleccionista);
                }

                if (!coleccionistasLista.isEmpty()) {
                    usersRecycler = (RecyclerView) getActivity().findViewById(R.id.usersRecycler);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    usersRecycler.setLayoutManager(linearLayoutManager);
                    SearchUsersAdapterRecyclerView adapter = new SearchUsersAdapterRecyclerView(coleccionistasLista, R.layout.cardview_search, getActivity());
                    usersRecycler.setAdapter(adapter);
                } else {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("No se encontraron vendedores");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
            Log.d(TAG,"On post Execute: ends");
        }

        private void postData(String number, String idAlbum){
            URL url = null;

            try{
                url = new URL(URL_SEARCH);
                JSONObject objParams = new JSONObject();
                objParams.put("number", number);
                objParams.put("idAlbum", idAlbum);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                writer.write(convertJSONObjectToStringParams(objParams));
                writer.flush();
                writer.close();
                outputStream.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer stringBuffer = new StringBuffer("");
                    String line;

                    while((line = bufferedReader.readLine()) != null){
                        stringBuffer.append(line);
                        break;
                    }

                    bufferedReader.close();
                    content = stringBuffer.toString();
                }
                    Log.d(TAG,"postData ends");

            }catch (Exception e){
                    e.printStackTrace();
            }
        }
        private String convertJSONObjectToStringParams(JSONObject params) throws JSONException, UnsupportedEncodingException {
            Log.d(TAG,"convert Starts");

            StringBuilder stringBuilder = new StringBuilder();
            boolean first = true;
            Iterator<String> iterator = params.keys();

            while(iterator.hasNext()){
                String key = iterator.next();
                Object value = params.get(key);
                if (first) first = false;
                else stringBuilder.append("&");
                stringBuilder.append(URLEncoder.encode(key,"UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(value.toString(),"UTF-8"));
            }
            return stringBuilder.toString();
        }
    }

    public void showToolbar(String title, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void showProgressDialog(String title) {
        progress.setMessage(title);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgressStyle(TextPaint.LINEAR_TEXT_FLAG);
        progress.setCancelable(false);
        progress.show();
    }
}
