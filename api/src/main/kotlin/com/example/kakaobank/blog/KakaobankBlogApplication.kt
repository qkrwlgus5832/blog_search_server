package com.example.kakaobank.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication(scanBasePackages = ["com.example.kakaobank.blog"])
@EnableJpaAuditing
@EntityScan("com.example.kakaobank.blog")
class KakaobankBlogApplication

fun main(args: Array<String>) {
    runApplication<KakaobankBlogApplication>(*args)
}
