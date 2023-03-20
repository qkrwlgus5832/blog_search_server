package com.example.kakaobank.blog

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap

inline fun <reified T> httpGet(
    url: String,
    httpHeaders: HttpHeaders,
): T? {
    val objectMapper = ObjectMapper()
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)

    val restTemplate = RestTemplateBuilder()
        .build()

    val httpEntity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(httpHeaders)

    val response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String::class.java)

    if (response.statusCode != HttpStatus.OK) {
        return null
    }

    return objectMapper.readValue(response.body, T::class.java)
}
