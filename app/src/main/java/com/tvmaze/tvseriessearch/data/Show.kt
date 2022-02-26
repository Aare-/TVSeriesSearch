package com.tvmaze.tvseriessearch.data

data class ImageData(
    val medium: String,
    val original: String
) {
}

data class Show(
    val id: Int,
    val url: String,
    val name: String,
    val genres: List<String>,
    val weight: Int,
    val image: ImageData?
) {
}

data class ShowWithScore(
    val score: Float,
    val show: Show
) {
}