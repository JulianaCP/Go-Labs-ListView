package com.example.juliana.listviewgithubusers.Conexiones;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juliana on 05/05/2018.
 */

public class Conexion {
    private static Conexion instance = null;
    private String baseurl;
    private final Retrofit retrofit;
    private Servidor servidor;

    private Conexion() {
        this.baseurl = "https://api.github.com";
        this.retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        this.servidor = retrofit.create(Servidor.class);
    }
    public static Conexion getInstance() {
        if(instance == null) {
            instance = new Conexion();
        }
        return instance;
    }
    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }
}
