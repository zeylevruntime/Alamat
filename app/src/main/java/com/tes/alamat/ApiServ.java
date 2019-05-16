package com.tes.alamat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiServ {
    @GET("/list_propinsi.json")
    Call<String> getProvince();

    @GET()
    Call<String> get(@Url String url);


    /*@GET("?apikey=491484bf")
    Call<MovieList> getMovies(
            @Query("s") String s,
            @Query("page") int page,
            @Query("type") String type,
            @Query("y") String year
    );
    @GET("?apikey=491484bf")
    Call<MovieDetail> getDetail(
            @Query("i") String s
    );*/


}
