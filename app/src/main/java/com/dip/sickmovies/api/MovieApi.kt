package com.dip.sickmovies.api

import androidx.lifecycle.LiveData
import com.dip.sickmovies.models.Movie
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface MovieApi {

    @GET("movie/top_rated")
    fun getTopRatedMovies(): LiveData<ApiResponse<List<Movie>>>

    @GET("movie/popular")
    fun getPopularMovies(): LiveData<ApiResponse<List<Movie>?>>

    @GET("movie/now_playing")
    fun getOnTheaterMovies(): LiveData<ApiResponse<List<Movie>>>

}