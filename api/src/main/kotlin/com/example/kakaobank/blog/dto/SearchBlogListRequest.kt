package com.example.kakaobank.blog.dto

import org.springframework.data.domain.Pageable

data class SearchBlogListRequest(
    val searchKeyword: String,
    val sortOrder: String = "a",
)
