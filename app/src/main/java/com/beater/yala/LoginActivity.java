package com.beater.yala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beater.yala.model.Coleccionista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    EditText username, password, username_profile,location_profile, phoneNumber_profile;
    Button login_btn;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        cleanSharedPreferences();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_btn);

        request = Volley.newRequestQueue(getApplicationContext());

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWebService();
            }
        });
    }

    //CARGAR INTERFAZ DE REGISTRO
    public void signUp(View view){
        Intent intent = new Intent(this,CreateAccountActivity.class);
        startActivity(intent);
    }

    //VALIDAR USUARIO CON WEB SERVICE
    private void loadWebService() {
        String url ="https://juanhb.000webhostapp.com/Iniciar_Sesion.php?" +
                            "username="+ username.getText().toString() +
                            "&password="+ password.getText().toString() +"";
        url = url.replace(" ","%20");
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        //Toast.makeText(getApplicationContext(),"Inicio de sesión exitoso"+ response,Toast.LENGTH_LONG).show();
        JSONArray json = response.optJSONArray("usuarios");
        //JSONObject json = new JSONObject((Map) response);
        //JSONArray jsonArray = json.optJSONArray("estado");
        JSONObject jsonObject;

        //JSONArray json = response.optJSONArray("usuarios");

        try {
            jsonObject = json.getJSONObject(0);

            Integer id = jsonObject.optInt("idColeccionista");
            String username =jsonObject.optString("usuario");
            String location =jsonObject.optString("ubicacion");
            String phoneNumber =jsonObject.optString("contacto");

            SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("id", id);
                editor.putString("username", username);
                editor.putString("location", location);
                editor.putString("phoneNumber", phoneNumber);
                editor.apply();

            Intent intent = new Intent(getApplicationContext(),ContainerActivity.class);
            startActivity(intent);
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"No se pudo iniciar sesión"+ error.toString(),Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
    }

    //BORRAR DATOS GUARDADOS EN SHARED PREFERENCES
    private void cleanSharedPreferences(){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

}
