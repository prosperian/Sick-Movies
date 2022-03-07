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
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "now_playing_id") val nowPlayingMovieId: Int,
)
