package com.tvmaze.tvseriessearch.data.source.network

import com.tvmaze.tvseriessearch.data.source.SourceProvider
import org.junit.Test

class TVMazeSourceProviderTest {

    @Test
    fun testTVMazeSourceProvider_returnShowsSuccessfully() {
        val request = TVMazeSourceProvider.buildProvider(SourceProvider::class.java)

        val response = request.getShows("girls").execute()

        assert(response.isSuccessful) { "Request unsuccessful, error code: ${response.code()}" }

        val showList = response.body()

        assert(showList != null) { "Result is null" }
        assert(showList?.isNotEmpty() == true) { "Result is empty" }
    }
}