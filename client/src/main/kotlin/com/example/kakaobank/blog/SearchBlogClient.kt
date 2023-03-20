package com.example.kakaobank.blog

interface SearchBlogClient {
    fun searchBlog(searchKeyword: String, sortOrder: String, page: Int, size: Int): BlogSearchResponse
}
