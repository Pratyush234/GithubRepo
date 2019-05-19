package com.example.praty.githubrepo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.praty.githubrepo.R;
import com.example.praty.githubrepo.helper.ItemClickListener;
import com.example.praty.githubrepo.model.Repositories;

import java.util.List;

//adapter class to attach the recyclerview with the retrofit data response
public abstract class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";
    private List<Repositories> mRepos;
    private ItemClickListener mListener;

    public MyAdapter(List<Repositories> mRepos, ItemClickListener mListener) {
        this.mRepos = mRepos;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_content,parent,false);
        final ViewHolder holder=new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v,holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mRepos.get(position).getFull_name());
        holder.description.setText(mRepos.get(position).getDescription());

        String timestamp=mRepos.get(position).getCreated_at();
        timestamp=timestamp.substring(0,10);
        timestamp="Created on "+timestamp;
        holder.created.setText(timestamp);
        holder.stars.setText(String.valueOf(mRepos.get(position).getStargazers_count()));

        //check for last item
        if(position>=(getItemCount()-1))
            load();
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+mRepos.size());
        return mRepos.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,created,stars;

        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.title);
            description=(TextView) itemView.findViewById(R.id.description);
            created=(TextView) itemView.findViewById(R.id.created);
            stars=(TextView) itemView.findViewById(R.id.stars);
        }
    }

    public abstract void load();
}
