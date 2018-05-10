package com.example.juliana.listviewgithubusers.Clases;

import java.util.ArrayList;

/**
 * Created by Juliana on 10/05/2018.
 */

public class Group {
    private String Name;
    private ArrayList<GitHubUser> gitHubUser;


    public Group(String name, ArrayList<GitHubUser> gitHubUser) {
        Name = name;
        this.gitHubUser = gitHubUser;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<GitHubUser> getGitHubUser() {
        return gitHubUser;
    }

    public void setGitHubUser(ArrayList<GitHubUser> gitHubUser) {
        this.gitHubUser = gitHubUser;
    }
}
