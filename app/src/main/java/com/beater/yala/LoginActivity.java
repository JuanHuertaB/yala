package com.beater.yala;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beater.yala.data.SessionManagement;
import com.beater.yala.dialogos.AlertDialogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.time.format.TextStyle;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    EditText username, password, username_profile,location_profile, phoneNumber_profile;
    Button login_btn;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    SessionManagement session;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Session Manager
        session = new SessionManagement(getApplicationContext());
        progress = new ProgressDialog(LoginActivity.this);

        if(session.isLoggedIn() == true){
            Intent intent = new Intent(getApplicationContext(),ContainerActivity.class);
            startActivity(intent);
            finish();
        }
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
        showProgressDialog("Iniciando Sesión ...");

        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (user.trim().length() > 0 && pass.trim().length() > 0) {
            String url = "https://juanhb.000webhostapp.com/Iniciar_Sesion.php?" +
                    "username=" + user +
                    "&password=" + pass + "";
            url = url.replace(" ", "%20");

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            request.add(jsonObjectRequest);

        }else{

            Toast.makeText(getApplicationContext(),"Ingrese su nombre de usuario y contraseña",Toast.LENGTH_SHORT).show();
            //alert.showAlertDialog(LoginActivity.this, "Error al iniciar sesión","Ingrese su nombre de usuario y contraseña",false);
        }

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONObject jsonObject;
        Integer valor = response.optInt("estado");

        if( valor == 1){
            JSONArray json = response.optJSONArray("usuarios");

            try {
            jsonObject = json.getJSONObject(0);

            String id = jsonObject.optString("idColeccionista");
            String username =jsonObject.optString("usuario");
            String location =jsonObject.optString("ubicacion");
            String phoneNumber =jsonObject.optString("contacto");

            session.createLoginSession(id,username,phoneNumber,location);

            progress.hide();

            Intent intent = new Intent(getApplicationContext(),ContainerActivity.class);
            startActivity(intent);
            finish();

            } catch (JSONException e) {
                 e.printStackTrace();

                }
        }else {
                Toast.makeText(getApplicationContext(),"Nombre de usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
               // alert.showAlertDialog(LoginActivity.this, "Error al iniciar sesión","Nombre de usuario o contraseña incorrectos",false);
            }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"No se pudo iniciar sesión"+ error.toString(),Toast.LENGTH_LONG).show();
        progress.hide();
    }

    public void showProgressDialog(String title) {
        progress.setMessage(title);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgressStyle(TextPaint.ANTI_ALIAS_FLAG);
        progress.setCancelable(false);
        progress.show();
    }
}
