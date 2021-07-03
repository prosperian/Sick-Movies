package com.dip.sickmovies.db


import androidx.lifecycle.LiveData
import androidx.room.*
import com.dip.sickmovies.NowPlaying
import com.dip.sickmovies.models.Movie
import com.dip.sickmovies.models.NowPlayingMovie
import com.dip.sickmovies.models.PopularMovie
import com.dip.sickmovies.models.TopRatedMovie

@Dao
interface MovieDao {

    @Insert(entity = Movie::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Insert(entity = PopularMovie::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPopularMovie(movies: List<PopularMovie>)

    @Insert(entity = TopRatedMovie::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTopRatedMovie(movies: List<TopRatedMovie>)

    @Insert(entity = NowPlayingMovie::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNowPlayingMovie(movies: List<NowPlayingMovie>)

    @Query("select * from Movie where id = :id")
    fun getMovie(id: Int): LiveData<Movie>

    @Transaction
    @Query("select * from Movie where id in (select distinct(popular_id) from PopularMovie)")
    fun getPopularMovies(): LiveData<List<Movie>?>

    @Query("select * from Movie where id in (select distinct(top_rated_id) from TopRatedMovie)")
    fun getTopRatedMovies(): LiveData<List<Movie>>

    @Query("select * from Movie where id in (select distinct(now_playing_id) from NowPlayingMovie)")
    fun getNowPlayingMovies(): LiveData<List<Movie>>

}