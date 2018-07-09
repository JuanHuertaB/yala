package com.beater.yala.model;

import java.io.Serializable;

/**
 * Created by JuanCarlos on 10/06/2018.
 */
public class Coleccionista  implements Serializable{

    private String id;
    private String username;
    private String password;
    private String location;
    private String phoneNumber;

    public Coleccionista(String id, String username, String password, String location, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public Coleccionista(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
