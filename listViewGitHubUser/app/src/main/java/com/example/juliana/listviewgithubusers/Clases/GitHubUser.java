package com.example.juliana.listviewgithubusers.Clases;

/**
 * Created by Juliana on 05/05/2018.
 */

public class GitHubUser {
    private String name;
    private String login;
    private int public_repos;
    private int followers;

    public GitHubUser(String name, String login, int public_repos, int followers) {
        this.name = name;
        this.login = login;
        this.public_repos = public_repos;
        this.followers = followers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}