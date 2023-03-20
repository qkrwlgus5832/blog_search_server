package com.example.kakaobank.blog.service

import com.example.kakaobank.blog.BlogSearchCount
import com.example.kakaobank.blog.BlogSearchResponse
import com.example.kakaobank.blog.SearchBlogClient
import com.example.kakaobank.blog.dto.SearchTop10KeywordResponse
import com.example.kakaobank.blog.repository.BlogSearchCountRepository
import com.example.kakaobank.blog.enums.KakaoBlogListSortingOrderEnum
import com.example.kakaobank.blog.enums.NaverBlogListSortingOrderEnum
import com.example.kakaobank.blog.exception.IncorrectSortingOrderException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
class SearchBlogService(
    private val searchBlogClient: SearchBlogClient,
    private val alternativeSearchBlogClient: SearchBlogClient,
    private val blogSearchCountRepository: BlogSearchCountRepository
) {
    private fun updateSearchCount(searchKeyword: String) {
        val blogSearchCount = blogSearchCountRepository.findFirstBySearchWord(searchKeyword)

        if (blogSearchCount != null) {
            blogSearchCount.setCount(blogSearchCount.getCount() + 1)
        } else {
            blogSearchCountRepository.save(
                BlogSearchCount(
                    searchWord = searchKeyword,
                    count = 1
                )
            )
        }
    }

    fun searchBlogList(
        searchKeyword: String,
        sortOrder: String,
        pageable: Pageable
    ): Page<BlogSearchResponse.Document> {
        if (!KakaoBlogListSortingOrderEnum.isExistByName(sortOrder)) {
            throw IncorrectSortingOrderException("sort값이 잘못되었습니다 a, r 중에 하나만 입력해주세요 : $sortOrder")
        }

        searchKeyword.let {
            if (it.contains(" ")) {
                updateSearchCount(it.split(" ")[1])
            } else {
                updateSearchCount(it)
            }
        }

        return try {
            val response =
                searchBlogClient.searchBlog(
                    searchKeyword,
                    KakaoBlogListSortingOrderEnum.toEnum(sortOrder)!!.code,
                    pageable.pageNumber,
                    pageable.pageSize
                )

            PageImpl(response.getDocumentList(), pageable, response.getMeta().pageableCount.toLong())
        } catch (exception: RuntimeException) {
            val response =
                alternativeSearchBlogClient.searchBlog(
                    searchKeyword,
                    NaverBlogListSortingOrderEnum.toEnum(sortOrder)!!.code,
                    pageable.pageNumber,
                    pageable.pageSize
                )

            PageImpl(response.getDocumentList(), pageable, response.getMeta().pageableCount.toLong())
        }
    }

    fun searchTop10Keyword(): List<SearchTop10KeywordResponse> {
        val result = blogSearchCountRepository.findFirst10ByOrderByCountDesc()

        return result.map {
            SearchTop10KeywordResponse(
                searchKeyword = it.getSearchWord(),
                searchCount = it.getCount()
            )
        }
    }
} 
