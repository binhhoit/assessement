package com.sts.data.repository

import com.sts.data.network.remote.ServiceAPI

class ArticleRepositoryImpl(private val api: ServiceAPI): ArticleRepository() {

    override suspend fun getArticlesPopularView(period: Int) =
        api.getArticlesPopularView(period = period)

    override suspend fun getArticlesSearch(keyword: String, page: Int) =
        api.getArticlesSearch(keywords = keyword,page = page)

}