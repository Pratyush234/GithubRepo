package com.example.praty.githubrepo.retrofit;

import com.example.praty.githubrepo.model.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search/repositories")
    Call<SearchResponse> getSearchResults(@Query("q") String query, @Query("sort") String sort, @Query("order")
                                          String order, @Query("page") int page);
}
