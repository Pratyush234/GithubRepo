package com.example.praty.githubrepo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.praty.githubrepo.adapter.MyAdapter;
import com.example.praty.githubrepo.helper.ItemClickListener;
import com.example.praty.githubrepo.model.Repositories;
import com.example.praty.githubrepo.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //recylerview
    RecyclerView mRecycler;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    List<Repositories> mRepositories;

    //viewmodel
    MainActivityViewModel mViewModel;

    //ui components
    EditText mEdit;
    ImageButton mSearch;
    ProgressBar mProgress;
    TextView mText;

    //global variables
    String mQuery=null;
    boolean isConnected=false;
    int mPage;
    int mTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instantaniation
        mRepositories=new ArrayList<>();
        mPage=1;
        mEdit=(EditText) findViewById(R.id.edit_text);
        mSearch=(ImageButton) findViewById(R.id.search_button);
        mProgress=(ProgressBar) findViewById(R.id.recycler_progress);
        mText=(TextView) findViewById(R.id.text_view);

        //setup the visible components
        mProgress.setVisibility(View.GONE);
        mText.setVisibility(View.VISIBLE);

        checkForInternet();
        setupRecyclerView();

        //Instantaniating the viewmodel
        mViewModel=ViewModelProviders.of(this).get(MainActivityViewModel.class);

        //onClickListener for the search button
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRepositories.size()>0)
                    mRepositories.clear();
                mPage=1;
                mText.setVisibility(View.GONE);
                mProgress.setVisibility(View.VISIBLE);
                processQuery();
            }
        });
    }

    //method to process the query entered by the user so that it's usable by the API
    private void processQuery() {
        mQuery=mEdit.getText().toString();
        mQuery=mQuery.replace(" ","+");
        mQuery=mQuery.substring(0,mQuery.length()-1);

        Log.d(TAG, "processQuery: query"+mQuery);
        mViewModel.queryRepo(mQuery,"stars","desc",mPage);
        getSearchResults();
    }

    //method to observe change in the MutableLiveData of repositories and update the adapter
    private void getSearchResults() {
        mViewModel.getRepositories().observe(this, new Observer<List<Repositories>>() {
            @Override
            public void onChanged(@Nullable List<Repositories> repositories) {
                if(repositories!=null) {
                    mRepositories.addAll(repositories);
                    mTotal = mRepositories.get(0).getTotal_pages()/30;
                }

                if(mRepositories.isEmpty()){
                    mText.setVisibility(View.VISIBLE);
                    mRecycler.setVisibility(View.GONE);
                }

                mAdapter.notifyDataSetChanged();
                mProgress.setVisibility(View.GONE);
            }
        });
    }

    //method to check for internet
    private void checkForInternet() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            //alert message to tell the user that he's not connected to the Internet
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

            builder.setMessage("You are not connected to the Internet")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            final AlertDialog dialog=builder.create();
            dialog.show();
        }
    }


    //method to setup the recyclerview
    private void setupRecyclerView(){
        mRecycler=(RecyclerView) findViewById(R.id.my_recycler);
        mLayoutManager=new GridLayoutManager(this, 1);
        mRecycler.setLayoutManager(mLayoutManager);

        if(isConnected){
            mAdapter=new MyAdapter(mRepositories, new ItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    //item click event
                    Intent intent=new Intent(MainActivity.this, WebActivity.class);
                    intent.putExtra("url",mRepositories.get(position).getHtml_url());
                    startActivity(intent);
                }
            }){
                //method to load more data using pagination
                @Override
                public void load() {
                    if(mPage<=mTotal) {
                        mProgress.setVisibility(View.VISIBLE);
                        mPage = mPage + 1;
                        processQuery();
                    }
                }
            };

            mRecycler.setAdapter(mAdapter);
        }

    }
}
