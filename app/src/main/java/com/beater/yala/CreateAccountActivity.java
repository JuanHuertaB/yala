package com.beater.yala;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.beater.yala.data.SessionManagement;

import org.json.JSONObject;

public class CreateAccountActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    EditText eTusername, eTpassword, eTlocation, eTphoneNumber;
    Button createAccount_btn;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        session = new SessionManagement(getApplicationContext());
        showToolbar(getResources().getString(R.string.toolbar_title_createAccount), true);

        eTusername = (EditText) findViewById(R.id.username_add);
        eTpassword = (EditText) findViewById(R.id.password_add);
        eTlocation = (EditText) findViewById(R.id.location_add);
        eTphoneNumber = (EditText) findViewById(R.id.phoneNumber_add);

        createAccount_btn = (Button) findViewById(R.id.createAccount_btn);

        request = Volley.newRequestQueue(getApplicationContext());

        createAccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWebService();
            }
        });
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void loadWebService() {
        String hostname = getString(R.string.host_name);

        String username = eTusername.getText().toString();
        String password = eTusername.getText().toString();
        String location = eTusername.getText().toString();
        String phoneNumber = eTusername.getText().toString();

        if (username.trim().length() > 0 && password.trim().length() > 0 && location.trim().length() > 0 && phoneNumber.trim().length() > 0 ) {

            String url = hostname+ "/Crear_Usuario.php?+" +
                    "usuario="+username+
                    "&contraseña="+password+
                    "&ubicacion="+location+
                    "&telefono="+phoneNumber+"";
            url = url.replace(" ","%20");

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null,this,this);
            request.add(jsonObjectRequest);
        }
        else{
            Toast.makeText(getApplicationContext(),"Uno de los datos no es válido",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(),"Se ha registrado correctamente",Toast.LENGTH_LONG).show();
        //progress.hide();
        eTusername.setText("");
        eTpassword.setText("");
        eTlocation.setText("");
        eTphoneNumber.setText("");

        Intent intent = new Intent(getApplication(),LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //progress.hide();
        Toast.makeText(getApplicationContext(),"No se ha podido registrar" + error.toString(),Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
    }

}
