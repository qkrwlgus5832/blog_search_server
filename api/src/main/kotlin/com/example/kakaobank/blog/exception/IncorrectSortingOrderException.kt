package com.example.kakaobank.blog.exception

/**
 * 잘못된 sorting 기준이 들어오면 던져주는 exception
 */
class IncorrectSortingOrderException(message: String) : RuntimeException(message)
