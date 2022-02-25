package com.tvmaze.tvseriessearch.data.source.network

import com.tvmaze.tvseriessearch.data.ShowList
import com.tvmaze.tvseriessearch.data.source.SourceEndpoints
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVMazeSourceProviderTest {

    @Test
    fun testTVMazeSourceProvider_returnShowsSuccessfully() {
        val request = TVMazeSourceProvider.buildProvider(SourceEndpoints::class.java)

        val call = request.getShows("girls")

        call.enqueue(object : Callback<ShowList> {
            override fun onResponse(call: Call<ShowList>, response: Response<ShowList>) {
                assert(response.isSuccessful)

                val showList = response.body()

                assert(showList != null)
                assert(showList?.results != null)
                assert(showList?.results?.isNotEmpty() == true)
            }

            override fun onFailure(call: Call<ShowList>, t: Throwable) {
                assert(false)
            }
        })
    }
}