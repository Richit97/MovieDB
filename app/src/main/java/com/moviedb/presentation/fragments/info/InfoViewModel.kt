package com.moviedb.presentation.fragments.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviedb.data.repository.RepoMovie
import com.moviedb.data.response.ResponseDetailsMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val repoMovie: RepoMovie): ViewModel() {

    private val _movie = MutableLiveData<ResponseDetailsMovie>()
    val movie:LiveData<ResponseDetailsMovie> get() = _movie

    fun detailsMovie(movie_id:Int) = viewModelScope.launch(Dispatchers.IO) {
        repoMovie.detailsMovie(movie_id).let {
            if (it.isSuccessful){
                _movie.postValue(it.body())
            }
        }
    }
}