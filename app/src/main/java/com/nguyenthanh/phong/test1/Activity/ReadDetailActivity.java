package com.nguyenthanh.phong.test1.Activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.nguyenthanh.phong.test1.Model.Movie;
import com.nguyenthanh.phong.test1.Network.Interface.MovieApiEndPointInterface;
import com.nguyenthanh.phong.test1.Network.Util.RetrofitUtil;
import com.nguyenthanh.phong.test1.R;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static org.parceler.Parcels.unwrap;

public class ReadDetailActivity extends AppCompatActivity {

    private final MovieApiEndPointInterface apiService = RetrofitUtil.create().create(MovieApiEndPointInterface.class);
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    private Disposable disposable;
    private Observable<Movie> movieListObservable;
    private int movieID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        movieID = Parcels.unwrap(getIntent().getParcelableExtra("id"));

        getMovieAt(movieID);
    }

    public void getMovieAt(Integer id) {
        movieListObservable = apiService.getMovieAt(id);
        this.disposable = movieListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Movie>() {
                    @Override
                    public void onNext(Movie value) {
                        updateView(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void updateView(Movie movie) {
        tvDetail.setText(movie.getBackdropPath()
                + movie.getOriginalLanguage()
                + movie.getOriginalTitle());
    }

    @Override
    protected void onDestroy() {
        this.disposable.dispose();
        super.onDestroy();
    }
}
