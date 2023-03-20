package com.example.kakaobank.blog.enums

enum class NaverBlogListSortingOrderEnum(val desc: String, val code: String) {
    a("정확도순", "sim"),
    r("최신순", "date");

    companion object {
        fun isExistByName(orderEnum: String): Boolean {
            return NaverBlogListSortingOrderEnum.values().any {
                it.name == orderEnum
            }
        }

        fun toEnum(orderEnum: String): NaverBlogListSortingOrderEnum? {
            return NaverBlogListSortingOrderEnum.values().firstOrNull {
                it.name == orderEnum
            }
        }
    }
} 
