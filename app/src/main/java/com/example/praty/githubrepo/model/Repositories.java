package com.example.praty.githubrepo.model;

import com.google.gson.annotations.SerializedName;

public class Repositories {

    @SerializedName("full_name")
    private String full_name;

    @SerializedName("html_url")
    private String html_url;

    @SerializedName("description")
    private String description;

    @SerializedName("stargazers_count")
    private int stargazers_count;

    @SerializedName("created_at")
    private String created_at;

    private int total_pages;

    public Repositories() {
    }

    public Repositories(String full_name, String html_url, String description, int stargazers_count, String created_at, int total_pages) {

        this.full_name = full_name;
        this.html_url = html_url;
        this.description = description;
        this.stargazers_count = stargazers_count;
        this.created_at = created_at;
        this.total_pages=total_pages;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
