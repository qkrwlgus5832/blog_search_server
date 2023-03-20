package com.example.kakaobank.blog

data class BlogSearchResponse (
    private val meta: Meta,
    private val documents: List<Document>
) {
    data class Meta(
        val totalCount: Int,
        val pageableCount: Int,
        val isEnd: Boolean
    )

    data class Document(
        val title: String,
        val contents: String,
        val url: String,
        val blogname: String,
        val thumbnail: String,
        val datetime: String
    )

    fun getDocumentList(): List<Document> {
        return documents
    }

    fun getMeta(): Meta {
        return meta
    }
}
