package com.sts.data.network.remote

import com.sts.data.model.response.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceAPI {

    @GET("mostpopular/v2/viewed/{period}.json")
    suspend fun getArticlesPopularView(@Path("period") period: Int): ArticlesResponse

    @GET("search/v2/articlesearch.json")
    suspend fun getArticlesSearch(@Query("q") keywords: String,
                                  @Query("page") page: Int): ArticlesResponse
}