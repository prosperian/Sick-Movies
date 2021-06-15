package com.dip.sickmovies.models

import androidx.room.*
import javax.annotation.concurrent.Immutable


@Immutable
@Entity(
    tableName = "NowPlayingMovie",
    foreignKeys = [
        ForeignKey(entity = Movie::class, parentColumns = ["id"], childColumns = ["now_playing_id"])
    ],
    indices = [Index("now_playing_id")]
)
data class NowPlayingMovie(
    @ColumnInfo(name = "now_playing_id") val nowPlayingMovieId: Int,
)
