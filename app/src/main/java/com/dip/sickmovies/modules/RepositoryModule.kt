package com.dip.sickmovies.modules

import com.dip.sickmovies.AppExecutors
import com.dip.sickmovies.api.MovieApi
import com.dip.sickmovies.db.MovieDao
import com.dip.sickmovies.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        movieApi: MovieApi,
        movieDao: MovieDao,
        appExecutors: AppExecutors
    ): MainRepository {
        return MainRepository(movieApi = movieApi, movieDao = movieDao, appExecutors = appExecutors)
    }

}