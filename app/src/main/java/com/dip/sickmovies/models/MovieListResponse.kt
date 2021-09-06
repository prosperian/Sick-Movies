package com.dip.sickmovies.models

data class MovieListResponse (
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
)
