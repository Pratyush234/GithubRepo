package com.example.praty.githubrepo.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.praty.githubrepo.model.Repositories;
import com.example.praty.githubrepo.repository.RepoRepository;

import java.util.List;

//viewmodel class for MainActivity
public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Repositories>> mRepositories;
    private RepoRepository mRepo;

    public void queryRepo(String query, String sort, String order, int page){
        mRepo=RepoRepository.getInstance();
        mRepositories=mRepo.getRepositories(query,sort,order,page);
    }

    public MutableLiveData<List<Repositories>> getRepositories() {
        return mRepositories;
    }
}
