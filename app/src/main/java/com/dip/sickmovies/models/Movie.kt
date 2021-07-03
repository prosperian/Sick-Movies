package com.dip.sickmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.properties.Delegates

@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Float,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val hasVideo: Boolean,
    val voteAverage: Float,
    val voteCount: Int,
){
    var dataFetchDate by Delegates.notNull<Long>()
}