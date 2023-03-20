package com.example.kakaobank.blog

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Table(name = "BLOG_SEARCH_COUNT", indexes = [Index(name = "idx_search_word", columnList = "search_word")])
@Entity
class BlogSearchCount(
    @Column(name = "search_word", nullable = false)
    private val searchWord: String,
    @Column(nullable = false)
    private var count: Long = 0
) {
    @Id
    @GeneratedValue
    private val id: Long? = null

    @CreatedDate
    @Column(updatable = false)
    private var createdAt: LocalDateTime? = null

    @LastModifiedDate
    private var updatedAt: LocalDateTime? = null

    fun setCount(count: Long) {
        this.count = count
    }

    fun getCount(): Long {
        return this.count
    }

    fun getSearchWord(): String {
        return this.searchWord
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BlogSearchCount) return false

        if (id != other.id) return false
        if (searchWord != other.searchWord) return false
        if (count != other.count) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + searchWord.hashCode()
        result = 31 * result + count.hashCode()
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        return result
    }
} 
