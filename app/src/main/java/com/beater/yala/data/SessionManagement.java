package com.beater.yala.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.beater.yala.LoginActivity;

import java.util.HashMap;

/**
 * Created by JuanCarlos on 24/06/2018.
 */
public class SessionManagement {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    //MODO DE LAS PREFERENCIAS COMPARTIDAS
    int PRIVATE_MODE = 0 ;

    private static final String PREF_NAME = "Yala_pref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "username";
    public static final String KEY_NUMBER = "phoneNumber";
    public static final String KEY_LOCATION = "location";

    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String id, String username, String phone, String location){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_ID,id);
        editor.putString(KEY_NAME,username);
        editor.putString(KEY_NUMBER,phone);
        editor.putString(KEY_LOCATION,location);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){

        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID,pref.getString(KEY_ID, null));
        user.put(KEY_NAME,pref.getString(KEY_NAME,null));
        user.put(KEY_LOCATION,pref.getString(KEY_LOCATION,null));
        user.put(KEY_NUMBER,pref.getString(KEY_NUMBER,null));

        return user;
    }

    public void checkLogin(){

        if(!this.isLoggedIn()){
            Intent intent = new Intent(_context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(intent);
        }
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(_context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN,false);
    }

}
