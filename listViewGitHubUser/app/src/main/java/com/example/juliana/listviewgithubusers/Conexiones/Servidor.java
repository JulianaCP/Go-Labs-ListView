package com.example.juliana.listviewgithubusers.Conexiones;

/**
 * Created by Juliana on 05/05/2018.
 */
import com.example.juliana.listviewgithubusers.Clases.GitHubUser;
import com.example.juliana.listviewgithubusers.Clases.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface Servidor {

    @GET("/users/{usuario}")
    Call<GitHubUser> servidorObtenerInfoGitHubUser(@Path("usuario") String usuario);

    @GET("/users{since}")
    Call<User> servidorObtenerInfoUser(@Path("since") String since);

    @GET("/users")
    Call<ArrayList<User>> getAction(@Query("since") String since);
}
