package com.dip.sickmovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.dip.sickmovies.api.Resource
import com.dip.sickmovies.base.LiveCoroutinesViewModel
import com.dip.sickmovies.models.Movie
import com.dip.sickmovies.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(private val mainRepository: MainRepository) : LiveCoroutinesViewModel() {

    private var _popularMovieList: MutableLiveData<Boolean> = MutableLiveData(true)
    val popularMovieList: LiveData<Resource<List<Movie>?>>

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {

        popularMovieList = _popularMovieList.switchMap {
            _isLoading.postValue(true)
            launchOnViewModelScope {
                this.mainRepository.getPopularMovies(
                    onSuccess = { _isLoading.postValue(false) },
                    onError = {}
                )
            }
        }

    }

}