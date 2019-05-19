package com.example.praty.githubrepo.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.praty.githubrepo.model.Repositories;
import com.example.praty.githubrepo.model.SearchResponse;
import com.example.praty.githubrepo.retrofit.ApiClient;
import com.example.praty.githubrepo.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//intermediate class to feed the viewmodel with the retrofit data
public class RepoRepository {
    private static final String TAG = "RepoRepository";

    private static RepoRepository instance;
    private ApiInterface apiService= ApiClient.getclient().create(ApiInterface.class);

    public static RepoRepository getInstance(){
        if(instance==null)
            instance=new RepoRepository();

        return instance;
    }

    //method to fetch the repositories using the API
    public MutableLiveData<List<Repositories>> getRepositories(String query, String sort, String order, int page){
        final MutableLiveData<List<Repositories>> repoData=new MutableLiveData<>();
        Call<SearchResponse> call=apiService.getSearchResults(query,sort,order,page);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse body=response.body();
                Log.d(TAG, "onResponse: called, body:"+body);

                if(body!=null){
                    List<Repositories> repos=body.getItems();
                    for(int i=0;i<repos.size();i++){
                        Log.d(TAG, "onResponse: repositories:"+repos.get(i).getFull_name());
                        repos.get(i).setTotal_pages(body.getTotal_count());
                    }
                    repoData.setValue(body.getItems());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: error:"+t.toString());
            }
        });

        return repoData;
    }
}
