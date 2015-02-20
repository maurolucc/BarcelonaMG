package com.example.mauro.barcelonamg.model;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Leisure")
public class Discoteca extends ParseObject {
    public String getName(){
        return getString("Name");
    }
    public void setName(String name){
        put("Name",name);
    }
    public String getDescripcio(){
        return getString("Descripcio");
    }
    public void setDescripcio(String descripcio){
        put("Descipcio",descripcio);
    }
    public String getType(){
        return getString("Type");
    }
    public void setType(String type){
        put("Type",type);
    }
    public ParseFile getIcon(){
        return getParseFile("Icon");
    }
    public void setIcon(ParseFile icon){
        put("Icon", icon);
    }
    public ParseFile getImage(){
        return getParseFile("Image");
    }
    public void setImage(ParseFile image){
        put("Image", image);
    }

    public static ParseQuery<Discoteca> getQuery() {
        return ParseQuery.getQuery(Discoteca.class);
    }

}
