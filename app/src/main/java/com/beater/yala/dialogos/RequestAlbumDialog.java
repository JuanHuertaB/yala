package com.beater.yala.dialogos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beater.yala.R;
import com.beater.yala.data.SessionManagement;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by JuanCarlos on 28/06/2018.
 */
public class RequestAlbumDialog extends AppCompatDialogFragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    private EditText eTnameAlbum, eTeditorial, eTcantidad;
    String nameAlbum, editorial, cantidad;
    SessionManagement session;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        session = new SessionManagement(getActivity());
        View view =  inflater.inflate(R.layout.dialog_request_album,null);

        eTnameAlbum = view.findViewById(R.id.req_nombreAlbum);
        eTeditorial= view.findViewById(R.id.req_editorial);
        eTcantidad = view.findViewById(R.id.req_cantidad);

        request = Volley.newRequestQueue(getActivity());
        builder.setView(view)
                .setTitle(Html.fromHtml("<font color='#000000'>Datos del álbum</font>"))
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, String> user = session.getUserDetails();
                        String idUser = user.get(SessionManagement.KEY_ID);

                        nameAlbum = eTnameAlbum.getText().toString();
                        editorial= eTeditorial.getText().toString();
                        cantidad = eTcantidad.getText().toString();

                        sendRequest(nameAlbum, editorial, cantidad,idUser);
                    }
                });
    return builder.create();
    }

    private void sendRequest(String nameAlbum, String editorial, String cantidad, String idColeccionista) {
        String url = "https://juanhb.000webhostapp.com/Crear_Solicitud.php?"+
                "titulo="+ nameAlbum +
                "&editorial="+ editorial +
                "&cantidad="+cantidad+
                "&idColeccionista=" + idColeccionista;
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        if (response.optInt("estado")== 1){
            //Toast.makeText(getActivity(),"Solicitud enviada correctamente",Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(getActivity(),"Error al enviar la solicitud",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

}
