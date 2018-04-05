package com.example.infolabsolution.lastmoviejar;

import com.example.infolabsolution.lastmoviejar.BuildConfig;
import com.example.infolabsolution.lastmoviejar.MovieModel;
import com.example.infolabsolution.lastmoviejar.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @GET("search/movie?api_key="+ BuildConfig.API_KEY)
    Call<MovieResponse> getItemSearch(@Query("query") String movie_name);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlaying(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcoming(@Query("api_key") String apiKey);


}
