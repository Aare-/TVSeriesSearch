package com.tvmaze.tvseriessearch.data.source

import com.tvmaze.tvseriessearch.data.ShowWithScore
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SourceEndpoints {
    @GET("search/shows")
    fun getShows(@Query("q") key: String): Call<List<ShowWithScore>>;
}