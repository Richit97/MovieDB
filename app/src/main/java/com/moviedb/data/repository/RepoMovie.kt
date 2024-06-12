package com.moviedb.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.moviedb.data.api.ApiServices
import com.moviedb.data.response.Result
import com.moviedb.domain.MoviePagingSource
import com.moviedb.utils.Constants
import javax.inject.Inject

class RepoMovie @Inject constructor(
    private val apiServices: ApiServices,
)  {
    fun getMovies(): LiveData<PagingData<Result>> {
        try {
            return Pager(
                config = PagingConfig(
                    pageSize = 1,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    MoviePagingSource(apiServices)
                }
            ).liveData
        }catch (e:Exception){
            throw e
        }
    }
   suspend fun detailsMovie(movie_id:Int) = apiServices.detailsMovie(movie_id,Constants.KEY)
}