package com.example.kakaobank.blog

data class KakaoBlogSearchParam(
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int,
)
