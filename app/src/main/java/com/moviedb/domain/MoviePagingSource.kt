package com.moviedb.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moviedb.data.api.ApiServices
import com.moviedb.data.response.Result
import com.moviedb.utils.Constants
import java.lang.Exception

class MoviePagingSource(private val apiServices: ApiServices):PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val position = params.key ?: 1
            val response = apiServices.getMovies(Constants.KEY,position)
            val data = response.body()?.results
            val responseData = mutableListOf<Result>()
            if (data != null) {
                responseData.addAll(data)
            }
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}