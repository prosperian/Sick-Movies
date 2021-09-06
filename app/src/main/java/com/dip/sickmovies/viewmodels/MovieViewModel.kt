package com.dip.sickmovies.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.dip.sickmovies.base.LiveCoroutinesViewModel
import com.dip.sickmovies.models.MovieListResponse
import com.dip.sickmovies.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(private val mainRepository: MainRepository) : LiveCoroutinesViewModel() {

    private var _popularMovieList: MutableLiveData<Boolean> = MutableLiveData(true)
    val popularMovieList: LiveData<MovieListResponse>

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        Log.d("MovieViewModel", "init");

        popularMovieList = _popularMovieList.switchMap {
            _isLoading.postValue(true)
            Log.d("MovieViewModel", "switch map");
            launchOnViewModelScope {
                this.mainRepository.getPopularMovies(
                    onSuccess = {
                        Log.d("MovieViewModel", "on success");
                        _isLoading.postValue(false)
                    },
                    onError = {
                        Log.d("MovieViewModel", it)
                        _isLoading.postValue(false)
                    }
                ).asLiveData()
            }
        }

    }

}