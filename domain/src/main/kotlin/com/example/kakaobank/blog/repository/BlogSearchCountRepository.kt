package com.example.kakaobank.blog.repository

import com.example.kakaobank.blog.BlogSearchCount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlogSearchCountRepository : JpaRepository<BlogSearchCount, Long> {
    fun findFirstBySearchWord(searchWord: String): BlogSearchCount?
    fun findFirst10ByOrderByCountDesc(): List<BlogSearchCount>
} 
