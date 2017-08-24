package com.nguyenthanh.phong.test1.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.nguyenthanh.phong.test1.Adapter.MovieAdapter;
import com.nguyenthanh.phong.test1.Model.MovieList;
import com.nguyenthanh.phong.test1.Network.Interface.MovieApiEndPointInterface;
import com.nguyenthanh.phong.test1.Network.Util.RetrofitUtil;
import com.nguyenthanh.phong.test1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final MovieApiEndPointInterface apiService = RetrofitUtil.create().create(MovieApiEndPointInterface.class);
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    private Disposable disposable;
    private Observable<MovieList> movieListObservable;
    private MovieList movieList;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getMovieList();
    }

    public void getMovieList() {
        movieListObservable = apiService.getMovieList();
        this.disposable = movieListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieList>() {
                    @Override
                    public void onNext(MovieList value) {
                        movieList = value;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        updateView();
                    }
                });
    }

    private void updateView() {
        movieAdapter = new MovieAdapter(getApplicationContext(), movieList);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvMovie.setLayoutManager(layoutManager);
        rvMovie.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        this.disposable.dispose();
        super.onDestroy();
    }
}
