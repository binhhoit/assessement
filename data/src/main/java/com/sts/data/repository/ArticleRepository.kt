package com.sts.data.repository

import com.sts.data.model.response.ArticlesResponse

abstract class ArticleRepository {
    abstract suspend fun getArticlesPopularView(period: Int): ArticlesResponse
    abstract suspend fun getArticlesSearch(keyword:String, page: Int):ArticlesResponse
}