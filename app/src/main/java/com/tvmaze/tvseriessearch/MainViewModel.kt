package com.tvmaze.tvseriessearch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tvmaze.tvseriessearch.data.ShowWithScore
import com.tvmaze.tvseriessearch.data.source.SourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataSource: SourceProvider
) : ViewModel() {

    val showSearchResult: MutableLiveData<List<ShowWithScore>> by lazy {
        MutableLiveData<List<ShowWithScore>>()
    }

    private var pendingQuery: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, exception -> Log.d("MainViewModel", exception.toString())}

    fun postNewShowSearchQuery(query: String) {
        // allow only the latest query to complete, we don't care about old query once new one is submitted
        pendingQuery?.cancel()

        pendingQuery = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = dataSource.getShows(query).awaitResponse()

            withContext(Dispatchers.Main) {
                if (result.isSuccessful)
                    showSearchResult.postValue(result.body()!!)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        pendingQuery?.cancel()
    }
}