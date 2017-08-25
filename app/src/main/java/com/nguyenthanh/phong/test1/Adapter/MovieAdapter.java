package com.nguyenthanh.phong.test1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nguyenthanh.phong.test1.Activity.ReadDetailActivity;
import com.nguyenthanh.phong.test1.Model.Movie;
import com.nguyenthanh.phong.test1.Model.MovieList;
import com.nguyenthanh.phong.test1.R;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private MovieList mMovieList;
    private Context mContext;

    // Pass in the contact array into the constructor
    public MovieAdapter(Context context, MovieList movieList) {
        mMovieList = movieList;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.movie_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Movie movie = mMovieList.getResults().get(position);

        // Set item views based on your views and data model
        Glide.with(mContext)
                .load("http://image.tmdb.org/t/p//w500" + movie.getPosterPath())
                .into(viewHolder.ivAvatar);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return 20;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_avatar)
        public void readDetail() {
            Intent intent = new Intent(mContext, ReadDetailActivity.class);
            intent.putExtra("id", Parcels.wrap(mMovieList.getResults().get(getAdapterPosition()).getId()));
            mContext.startActivity(intent);
        }
    }
}
