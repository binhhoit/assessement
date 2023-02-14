package com.sts.data.model.response

import com.google.gson.annotations.SerializedName
import com.sts.data.model.Article

data class ArticlesResponse(
    @SerializedName("status") val status: String,
    @SerializedName("num_results") val numberResult: Int,
    @SerializedName("results") val results: List<Article>,
    @SerializedName("response") val response: DocArticleResponse)

data class DocArticleResponse(@SerializedName("docs") val docs: List<Article>)