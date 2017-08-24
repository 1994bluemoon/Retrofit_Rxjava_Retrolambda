package com.nguyenthanh.phong.test1.Network.Interface;

import com.nguyenthanh.phong.test1.Model.Movie;
import com.nguyenthanh.phong.test1.Model.MovieList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiEndPointInterface {
    @GET("popular")
    Observable<MovieList> getMovieList();

    @GET("{id}")
    Observable<Movie> getMovieAt(@Path("id") Integer id);
}
