package com.example.kakaobank.blog

import com.example.kakaobank.blog.exception.HttpFailedException
import com.example.kakaobank.blog.exception.InvalidArgumentException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class NaverSearchBlogClient(): SearchBlogClient {
    companion object {
        const val PATH = "/v1/search/blog.json"
        const val MIN_PAGE_VALUE = 1
        const val MAX_PAGE_VALUE = 100
        const val MIN_SIZE_VALUE = 1
        const val MAX_SIZE_VALUE = 100
    }

    @Value("\${naver.url}")
    lateinit var naverUrl: String

    @Value("\${naver.client-id}")
    lateinit var naverClientId: String

    @Value("\${naver.client-secret}")
    lateinit var naverClientSecret: String

    override fun searchBlog(searchKeyword: String, sortOrder: String, page: Int, size: Int): BlogSearchResponse {
        val parameter = NaverBlogSearchParam(
            query = searchKeyword,
            display = size,
            start = page,
            sort = sortOrder
        )

        val httpHeaders = HttpHeaders().apply {
            accept = listOf(MediaType.ALL)
            this.add("X-Naver-Client-Id", naverClientId)
            this.add("X-Naver-Client-Secret", naverClientSecret)
        }

        val urlBuilder = UriComponentsBuilder.fromHttpUrl(naverUrl + PATH)
            .queryParam("query", parameter.query)
            .queryParam("display", parameter.display)
            .queryParam("start", parameter.start)
            .queryParam("sort", parameter.sort)
            .build()


        if (page !in (MIN_PAGE_VALUE..MAX_PAGE_VALUE)) {
            throw InvalidArgumentException("page값은 ${MIN_PAGE_VALUE}에서 ${MAX_PAGE_VALUE} 사이의 값이여야 합니다")
        }

        if (size !in (MIN_SIZE_VALUE..MAX_SIZE_VALUE)) {
            throw InvalidArgumentException("size값은 ${MIN_SIZE_VALUE}에서 ${MAX_SIZE_VALUE} 사이의 값이여야 합니다")
        }

        try {
            return httpGet<NaverBlogSearchResponse>(urlBuilder.toUriString(), httpHeaders)!!.toBlogSearchResponse()
        } catch (exception: RuntimeException) {
            throw HttpFailedException("http 통신 중 오류가 발생했습니다")
        }
    }
} 
