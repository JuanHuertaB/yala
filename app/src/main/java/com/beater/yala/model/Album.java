package com.beater.yala.model;

/**
 * Created by JuanCarlos on 4/06/2018.
 */
public class Album {

    private String picture;
    private String albumName;
    private String editorial;
    private int total;
    private int completadas;
    private int repetidas;

    public Album(String picture, String albumName, String editorial, int total, int completadas, int repetidas) {
        this.picture = picture;
        this.albumName = albumName;
        this.editorial = editorial;
        this.total = total;
        this.completadas = completadas;
        this.repetidas = repetidas;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCompletadas() {
        return completadas;
    }

    public void setCompletadas(int completadas) {
        this.completadas = completadas;
    }

    public int getRepetidas() {
        return repetidas;
    }

    public void setRepetidas(int repetidas) {
        this.repetidas = repetidas;
    }
}
