package com.home.tribalapp.data.model

data class SearchResponse(
    val total: Int,
    val total_pages: Int,
    val results: List<Image>
)
