package com.sts.feature.screen.articels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.sts.data.model.Article
import kotlinx.coroutines.flow.Flow

abstract class ArticlesViewModel : ViewModel() {
    abstract val articlesUiState: MutableState<ArticlesState>

    abstract fun loadArticlesPopularViewMode()

    abstract fun setKeywordSearch(keyword:String)

    abstract val loadArticlesSearch : Flow<PagingData<Article>>
}