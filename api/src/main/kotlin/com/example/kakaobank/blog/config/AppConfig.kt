package com.example.kakaobank.blog.config

import com.example.kakaobank.blog.KakaoSearchBlogClient
import com.example.kakaobank.blog.NaverSearchBlogClient
import com.example.kakaobank.blog.SearchBlogClient
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["com.example.kakaobank.blog"])
class AppConfig {
    @Bean
    fun searchBlogClient(): SearchBlogClient {
        return KakaoSearchBlogClient()
    }

    @Bean
    fun alternativeSearchBlogClient(): SearchBlogClient {
        return NaverSearchBlogClient()
    }
}
