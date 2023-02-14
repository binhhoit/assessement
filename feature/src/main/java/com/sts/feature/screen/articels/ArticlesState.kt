package com.sts.feature.screen.articels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sts.data.model.Article

data class ArticlesState(
    val isLoading: MutableState<Boolean> = mutableStateOf(true),
    val throwError: MutableState<String> = mutableStateOf(""),
    val dataArticles: SnapshotStateList<Article> = mutableStateListOf()
)