package com.dip.sickmovies.modules

import com.dip.sickmovies.utils.Utils.BASE_URL
import com.dip.sickmovies.api.MovieApi
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .baseUrl(BASE_URL)
            .build()

    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

}