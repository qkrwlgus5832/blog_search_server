package com.example.kakaobank.blog.enums

enum class KakaoBlogListSortingOrderEnum(val desc: String, val code: String) {
    a("정확도순", "accurancy"),
    r("최신순", "recency");

    companion object {
        fun isExistByName(orderEnum: String): Boolean {
            return KakaoBlogListSortingOrderEnum.values().any {
                it.name == orderEnum
            }
        }

        fun toEnum(orderEnum: String): KakaoBlogListSortingOrderEnum? {
            return KakaoBlogListSortingOrderEnum.values().firstOrNull {
                it.name == orderEnum
            }
        }
    }
} 
