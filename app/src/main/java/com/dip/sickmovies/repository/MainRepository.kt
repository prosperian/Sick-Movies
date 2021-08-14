package com.dip.sickmovies.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import com.dip.sickmovies.api.MovieApi
import com.dip.sickmovies.db.MovieDao
import com.dip.sickmovies.AppExecutors
import com.dip.sickmovies.models.Movie
import com.skydoves.sandwich.*
import com.skydoves.whatif.whatIfNotNull
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class MainRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val appExecutors: AppExecutors
) : Repository {

    val TAG: String = "MainRepository"

    init {
        Log.d(TAG, "init")
    }

    @WorkerThread
    fun getPopularMovies(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = flow {

        val popMovies: List<Movie> = movieDao.getPopularMovies()
        if (popMovies.isEmpty()) {
            movieApi.getPopularMovies()
                .suspendOnSuccess {
                    data.whatIfNotNull{
                        emit(it)
                        onSuccess()
                    }
                }

                .onError {
                    onError(message())
                }

                .onException {
                    onError(message())
                }

        } else {
            onSuccess()
        }

    }.flowOn(Dispatchers.IO)


}