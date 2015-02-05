package com.example.mauro.barcelonamg.model;

import android.graphics.Bitmap;

public class Dades {
    public String nom;
    public String text;
    public Bitmap icono;
    public Bitmap imatge;
    public String queSoc;

    public Dades(String nom, String text, Bitmap icono, Bitmap imatge,String queSoc) {
        this.nom = nom;
        this.text = text;
        this.icono = icono;
        this.imatge = imatge;
        this.queSoc = queSoc;
    }
}
