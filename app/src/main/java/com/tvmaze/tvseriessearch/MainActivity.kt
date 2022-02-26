package com.tvmaze.tvseriessearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.tvmaze.tvseriessearch.data.ShowWithScore
import com.tvmaze.tvseriessearch.ui.theme.TVSeriesSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVSeriesSearchTheme {
                ActivityPage()
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun ActivityPage() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SearchBar()
                ShowsList()
            }
        }
    }

    @Composable
    fun SearchBar() {
        val searchQuery = viewModel.currentSearchQuery.collectAsState()

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            value = searchQuery.value,
            maxLines = 1,
            label = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                viewModel.currentSearchQuery.value = it

                if (viewModel.currentSearchQuery.value.length >= 3)
                    viewModel.postNewShowSearchQuery(viewModel.currentSearchQuery.value)
                else
                    viewModel.clearShowSearch()
            })
    }

    @Composable
    fun ShowsList() {
        var searchResultList = viewModel.showSearchResult.observeAsState()
        var listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchResultList.value!!) {
                searchItem -> ShowListItem(searchItem)
            }
        }
    }

    @Composable
    fun ShowListItem(showWithScore: ShowWithScore) {
        val painter = rememberImagePainter(
            data = showWithScore.show.image?.medium,
        )

        Card(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth(),
            elevation = 2.dp,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Row (
                modifier = Modifier.padding(10.dp, 5.dp)
            )
            {
                Card(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    elevation = 2.dp
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(120.dp)
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = showWithScore.show.name,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = showWithScore.show.genres.joinToString(", "),
                        modifier = Modifier.fillMaxWidth(),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}