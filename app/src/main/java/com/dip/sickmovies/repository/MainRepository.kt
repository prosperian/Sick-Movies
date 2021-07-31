package com.dip.sickmovies.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.dip.sickmovies.api.MovieApi
import com.dip.sickmovies.db.MovieDao
import com.dip.sickmovies.AppExecutors
import com.dip.sickmovies.MainActivity
import com.dip.sickmovies.api.NetworkBoundResource
import com.dip.sickmovies.api.Resource
import com.dip.sickmovies.models.Movie
import com.dip.sickmovies.models.PopularMovie
import com.skydoves.sandwich.ApiResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class MainRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val appExecutors: AppExecutors
) : Repository {

    val TAG: String? = MainRepository::class.simpleName

    @WorkerThread
    fun getPopularMovies(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ): LiveData<Resource<List<Movie>?>> {

        return object : NetworkBoundResource<List<Movie>, List<Movie>>(appExecutors) {

            override fun loadFromDB(): LiveData<List<Movie>?> {
                Log.d(TAG, "loading from db")

                return movieDao.getPopularMovies()
            }

            override fun loadFromNetwork(): LiveData<ApiResponse<List<Movie>?>> {
                Log.d(TAG, "loading from network")
                return movieApi.getPopularMovies()
            }

            override fun saveCallResult(item: List<Movie>) {
                Log.d(TAG, "save results")

                val popularMovies = mutableListOf<PopularMovie>()
                for (i in item) {
                    i.dataFetchDate = Date().time
                    val p = PopularMovie(popularMovieId = i.id)
                    popularMovies.add(p)
                }
                movieDao.insertAll(item)
                movieDao.insertAllPopularMovie(popularMovies)
            }

            override fun getDataFetchDate(data: List<Movie>?): Long? {
                Log.d(TAG, "get fetch date")
                val movie = data?.get(0)
                return movie?.dataFetchDate
            }

        }.result

    }


}