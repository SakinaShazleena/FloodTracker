package com.example.floodtracker;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Flood {

    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("latitud")
    @Expose
    public String latitud;
    @SerializedName("longitud")
    @Expose
    public String longitud;

}