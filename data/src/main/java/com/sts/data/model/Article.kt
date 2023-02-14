package com.sts.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id") val id: String?,
    @SerializedName("_id") val _id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("published_date") val publishedDate: String?,
    @SerializedName("abstract") val abstract: String?,
    @SerializedName("pub_date") val pubDate: String?)
