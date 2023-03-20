package com.example.kakaobank.blog

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class NaverBlogSearchResponse @JsonCreator constructor(
    @JsonProperty("lastBuildDate")
    val lastBuildDate: String,
    @JsonProperty("total")
    val total: Int,
    @JsonProperty("start")
    val start: Int,
    @JsonProperty("display")
    val display: Int,
    @JsonProperty("items")
    val items: List<Item>
) {
    fun toBlogSearchResponse(): BlogSearchResponse {
        return BlogSearchResponse(
            meta = BlogSearchResponse.Meta(
                totalCount = total,
                pageableCount = total,
                isEnd = if (total % display == 0) (total / display) == start else (total / display) + 1 == start
            ),
            documents = items.map {
                BlogSearchResponse.Document(
                    title = it.title,
                    contents = it.description,
                    url = it.link,
                    blogname = it.bloggername,
                    thumbnail = "",
                    datetime = it.postdate
                )
            }
        )
    }
    data class Item(
        @JsonProperty("title")
        val title: String,
        @JsonProperty("link")
        val link: String,
        @JsonProperty("description")
        val description: String,
        @JsonProperty("bloggername")
        val bloggername: String,
        @JsonProperty("bloggerlink")
        val bloggerlink: String,
        @JsonProperty("postdate")
        val postdate: String
    )
}
