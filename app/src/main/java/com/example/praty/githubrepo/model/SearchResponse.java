package com.example.praty.githubrepo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("total_count")
    private int total_count;

    @SerializedName("incomplete_results")
    private boolean incomplete_results;

    @SerializedName("items")
    private List<Repositories> items;

    public SearchResponse() {
    }

    public SearchResponse(int total_count, boolean incomplete_results, List<Repositories> items) {

        this.total_count = total_count;
        this.incomplete_results = incomplete_results;
        this.items = items;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<Repositories> getItems() {
        return items;
    }

    public void setItems(List<Repositories> items) {
        this.items = items;
    }
}
