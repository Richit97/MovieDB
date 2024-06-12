package com.moviedb.data.api

import com.moviedb.data.response.ResponseDetailsMovie
import com.moviedb.data.response.ResponseMovie
import com.moviedb.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @GET(Constants.LIST_MOVIES)
    suspend fun getMovies(
        @Query("api_key") key:String,
        @Query("page") page:Int
    ):Response<ResponseMovie>

    @GET(Constants.DETAILS_MOVIE)
    suspend fun detailsMovie(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") key:String
    ):Response<ResponseDetailsMovie>

}