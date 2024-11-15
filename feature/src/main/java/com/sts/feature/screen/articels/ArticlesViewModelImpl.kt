package com.sts.feature.screen.articels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.sts.data.domain.article.PopularViewsUseCase
import com.sts.data.domain.article.SearchUseCase
import com.sts.data.repository.ArticleRepository
import com.sts.feature.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf

class ArticlesViewModelImpl(private val popularViewsUseCase: PopularViewsUseCase)
    : ArticlesViewModel() {

    private var keywordSearch = ""

    override val articlesUiState = mutableStateOf(ArticlesState())

    override fun loadArticlesPopularViewMode() {
        popularViewsUseCase.execute(1)
            .flowOn(Dispatchers.IO)
            .onStart {
                articlesUiState.value = articlesUiState.value.apply {
                    isLoading.value = true
                }
            }
            .onEach {
                articlesUiState.value = articlesUiState.value.apply {
                    dataArticles.addAll(it.results)
                }
            }.catch { _ ->
                articlesUiState.value = articlesUiState.value.apply {
                    throwError.value = Constants.ERROR_LOAD_FAILED
                }
            }.onCompletion {
                articlesUiState.value = articlesUiState.value.apply {
                    isLoading.value = false
                }
            }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    override fun setKeywordSearch(keyword: String) {
        keywordSearch = keyword
    }

    override val loadArticlesSearch = Pager(PagingConfig(pageSize = 20)){
        GlobalContext.get().get<SearchUseCase>{ parametersOf(keywordSearch) }
    }.flow.cachedIn(viewModelScope)

}
