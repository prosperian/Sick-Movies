package com.dip.sickmovies.api

import androidx.lifecycle.LiveData
import com.dip.sickmovies.models.Movie
import com.dip.sickmovies.models.MovieListResponse
import com.dip.sickmovies.utils.Utils.TOKEN
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface MovieApi {

    @Headers("Authorization: Bearer $TOKEN")
    @GET("movie/top_rated")
    fun getTopRatedMovies(): LiveData<ApiResponse<List<Movie>>>

    @Headers("Authorization: Bearer $TOKEN")
    @GET("movie/popular")
    suspend fun getPopularMovies(): ApiResponse<MovieListResponse>

    @Headers("Authorization: Bearer $TOKEN")
    @GET("movie/now_playing")
    fun getOnTheaterMovies(): LiveData<ApiResponse<List<Movie>>>

}