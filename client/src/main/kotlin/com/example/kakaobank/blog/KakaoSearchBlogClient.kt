package com.example.kakaobank.blog

import com.example.kakaobank.blog.exception.HttpFailedException
import com.example.kakaobank.blog.exception.InvalidArgumentException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class KakaoSearchBlogClient: SearchBlogClient {
    companion object {
        const val PATH = "/v2/search/blog"
        const val MIN_PAGE_VALUE = 1
        const val MAX_PAGE_VALUE = 50
        const val MIN_SIZE_VALUE = 1
        const val MAX_SIZE_VALUE = 50
    }

    @Value("\${kakao.url}")
    lateinit var kakaoUrl: String

    @Value("\${kakao.token}")
    lateinit var kakaoToken: String

    override fun searchBlog(searchKeyword: String, sortOrder: String, page: Int, size: Int): BlogSearchResponse {
        val parameter = KakaoBlogSearchParam(
            query = searchKeyword,
            sort = sortOrder,
            page = page,
            size = size
        )

        val httpHeaders = HttpHeaders().apply {
            accept = listOf(MediaType.APPLICATION_JSON)
            this.add(HttpHeaders.AUTHORIZATION, "KakaoAK $kakaoToken")
        }

        val urlBuilder = UriComponentsBuilder.fromHttpUrl(kakaoUrl + PATH)
            .queryParam("query", parameter.query)
            .queryParam("sort", parameter.sort)
            .queryParam("page", parameter.page)
            .queryParam("size", parameter.size)
            .build()

        if (page !in (MIN_PAGE_VALUE..MAX_PAGE_VALUE)) {
            throw InvalidArgumentException("page값은 ${MIN_PAGE_VALUE}에서 ${MAX_PAGE_VALUE} 사이의 값이여야 합니다")
        }

        if (size !in (MIN_SIZE_VALUE..MAX_SIZE_VALUE)) {
            throw InvalidArgumentException("size값은 ${MIN_SIZE_VALUE}에서 ${MAX_SIZE_VALUE} 사이의 값이여야 합니다")
        }

        try {
            val response =
                httpGet<KakaoBlogSearchResponse>(urlBuilder.toUriString(), httpHeaders)

            return response!!.toBlogSearchResponse()
        } catch (exception: RuntimeException) {
            throw HttpFailedException("http 통신 중 오류가 발생했습니다")
        }
    }
} 
