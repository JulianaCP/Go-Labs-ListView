package com.example.juliana.listviewgithubusers.Clases;

/**
 * Created by Juliana on 05/05/2018.
 */

public class User {
    private String login;
    private int id;


    public User(String login, int id) {
        this.id = id;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
