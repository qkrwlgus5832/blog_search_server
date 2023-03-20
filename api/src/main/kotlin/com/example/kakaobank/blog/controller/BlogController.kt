package com.example.kakaobank.blog.controller

import com.example.kakaobank.blog.BlogSearchResponse
import com.example.kakaobank.blog.dto.ErrorResponse
import com.example.kakaobank.blog.service.SearchBlogService
import com.example.kakaobank.blog.dto.SearchBlogListRequest
import com.example.kakaobank.blog.dto.SearchTop10KeywordResponse
import com.example.kakaobank.blog.exception.HttpFailedException
import com.example.kakaobank.blog.exception.IncorrectSortingOrderException
import com.example.kakaobank.blog.exception.InvalidArgumentException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(BlogController.URL_PATH)
class BlogController(
    private val searchBlogService: SearchBlogService
) {

    companion object {
        const val URL_PATH = "/blog"
    }

    @GetMapping("/list")
    fun listBlog(request: SearchBlogListRequest, @PageableDefault(size = 10, page = 1) pageable: Pageable): Page<BlogSearchResponse.Document> {
        return searchBlogService.searchBlogList(request.searchKeyword, request.sortOrder, pageable)
    }

    @GetMapping("list/top10/keyword")
    fun listTop10Keyword(): List<SearchTop10KeywordResponse> {
        return searchBlogService.searchTop10Keyword()
    }

    @ExceptionHandler
    fun handleIncorrectSortingOrderException(e: IncorrectSortingOrderException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(ErrorResponse.build(e.message ?: ""))
    }

    @ExceptionHandler
    fun handleInvalidArgumentException(e: InvalidArgumentException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(ErrorResponse.build(e.message ?: ""))
    }

    @ExceptionHandler
    fun handleHttpFailedException(e: HttpFailedException): ResponseEntity<*> {
        return ResponseEntity.internalServerError().body(ErrorResponse.build(e.message ?: ""))
    }
} 
