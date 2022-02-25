package com.tvmaze.tvseriessearch.data

data class Show(
    val id: Int,
    val url: String,
    val name: String,
    val genres: List<String>,
    val weight: Int
) {
}

data class ShowWithScore(
    val score: Float,
    val show: Show
) {
}

data class ShowList(
    val results: List<ShowWithScore>
){

}