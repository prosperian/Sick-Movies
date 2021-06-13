package com.dip.sickmovies.api

import com.dip.sickmovies.models.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Call<List<Movie>>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Call<List<Movie>>

    @GET("movie/now_playing")
    suspend fun getOnTheaterMovies(): Call<List<Movie>>

}