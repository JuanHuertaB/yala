package com.beater.yala;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CreateAccountActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    EditText username, password, location, phoneNumber;
    Button createAccount_btn;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        showToolbar(getResources().getString(R.string.toolbar_title_createAccount), true);

        username = (EditText) findViewById(R.id.username_add);
        password = (EditText) findViewById(R.id.password_add);
        location = (EditText) findViewById(R.id.location_add);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber_add);
        createAccount_btn = (Button) findViewById(R.id.createAccount_btn);

        request = Volley.newRequestQueue(getApplicationContext());

        createAccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWebService();
            }
        });

        ;
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void loadWebService() {
        String url = "https://juanhb.000webhostapp.com/Crear_Usuario.php?usuario="+
                        username.getText().toString()+"&contraseña="+
                        password.getText().toString()+"&ubicacion="+
                        phoneNumber.getText().toString()+"&telefono="+
                        location.getText().toString()+"";
        url = url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(),"Se ha registrado correctamente",Toast.LENGTH_LONG).show();
        //progress.hide();
        username.setText("");
        password.setText("");
        location.setText("");
        phoneNumber.setText("");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //progress.hide();
        Toast.makeText(getApplicationContext(),"No se ha podido registrar" + error.toString(),Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
    }

}
