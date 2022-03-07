package com.dip.sickmovies.models

import androidx.room.*
import javax.annotation.concurrent.Immutable

@Immutable
@Entity(
    tableName = "TopRatedMovie",
    foreignKeys = [
        ForeignKey(entity = Movie::class, parentColumns = ["id"], childColumns = ["top_rated_id"])
    ],
    indices = [Index("top_rated_id")]
)
data class TopRatedMovie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "top_rated_id") val topRatedMovieId: Int,
)