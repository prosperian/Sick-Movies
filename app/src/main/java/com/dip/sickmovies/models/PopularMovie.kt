package com.dip.sickmovies.models

import androidx.room.*
import javax.annotation.Nullable
import javax.annotation.concurrent.Immutable

@Immutable
@Entity(
    tableName = "PopularMovie",
    foreignKeys = [
        ForeignKey(entity = Movie::class, parentColumns = ["id"], childColumns = ["popular_id"])
    ],
    indices = [Index("popular_id")]
)
data class PopularMovie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "popular_id") val popularMovieId: Int,
)