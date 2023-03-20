package com.example.kakaobank.blog

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoBlogSearchResponse @JsonCreator constructor(
    @JsonProperty("meta")
    val meta: Meta,
    @JsonProperty("documents")
    val documents: List<Document>
) {
    fun toBlogSearchResponse(): BlogSearchResponse {
        return BlogSearchResponse(
            meta = this.meta.let {
                BlogSearchResponse.Meta(
                    totalCount = it.totalCount,
                    pageableCount = it.pageableCount,
                    isEnd = it.isEnd
                )
            },
            documents = this.documents.map {
                BlogSearchResponse.Document(
                    title = it.title,
                    contents =  it.contents,
                    url = it.url,
                    blogname = it.blogname,
                    thumbnail = it.thumbnail,
                    datetime = it.datetime
                )
            }
        )
    }

    data class Meta @JsonCreator constructor(
        @JsonProperty("total_count")
        val totalCount: Int,
        @JsonProperty("pageable_count")
        val pageableCount: Int,
        @JsonProperty("is_end")
        val isEnd: Boolean
    )

    data class Document @JsonCreator constructor(
        @JsonProperty("title")
        val title: String,
        @JsonProperty("contents")
        val contents: String,
        @JsonProperty("url")
        val url: String,
        @JsonProperty("blogname")
        val blogname: String,
        @JsonProperty("thumbnail")
        val thumbnail: String,
        @JsonProperty("datetime")
        val datetime: String,
    )
}
