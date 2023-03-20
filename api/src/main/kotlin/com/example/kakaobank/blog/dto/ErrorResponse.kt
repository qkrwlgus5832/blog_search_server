package com.example.kakaobank.blog.dto

class ErrorResponse private constructor (
    val message: String,
) {
    companion object {
        fun build(message: String): ErrorResponse {
            return ErrorResponse(message)
        }
    }
}

