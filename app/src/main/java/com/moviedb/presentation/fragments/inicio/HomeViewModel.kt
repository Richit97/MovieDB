package com.moviedb.presentation.fragments.inicio

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moviedb.data.repository.RepoMovie
import com.moviedb.data.response.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repoMovie: RepoMovie): ViewModel() {

    fun getListMovie(): LiveData<PagingData<Result>> {
        return repoMovie.getMovies().cachedIn(viewModelScope)
    }
}