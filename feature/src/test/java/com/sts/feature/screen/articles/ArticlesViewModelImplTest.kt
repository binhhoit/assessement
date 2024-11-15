package com.sts.feature.screen.articles

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sts.data.domain.article.PopularViewsUseCase
import com.sts.data.model.Article
import com.sts.data.model.response.ArticlesResponse
import com.sts.data.model.response.DocArticleResponse
import com.sts.feature.screen.articels.ArticlesViewModel
import com.sts.feature.screen.articels.ArticlesViewModelImpl
import com.sts.feature.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ArticlesViewModelImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var popularViewsUseCase: PopularViewsUseCase

    private lateinit var viewModel: ArticlesViewModel

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ArticlesViewModelImpl(popularViewsUseCase)
    }

    @Test
    fun `test loadArticlesPopularViewMode success`() = runTest {
        // Given
        val page = 1
        val article1 = Article("1", "Title 1", "Summary 1", "Image 1", "Author 1", "Date 1")
        val article2 = Article("2", "Title 2", "Summary 2", "Image 2", "Author 2", "Date 2")
        val popularViewsResult = ArticlesResponse(results = listOf(article1, article2), status = "", numberResult = 2, response = DocArticleResponse(docs = listOf()))
        `when`(popularViewsUseCase.execute(page)).thenReturn(flowOf(popularViewsResult))

        // Then
        viewModel.loadArticlesPopularViewMode()

        // Result
        viewModel.articlesUiState.value.isLoading.value = true
        advanceTimeBy(500)
        viewModel.articlesUiState.value.isLoading.value.let {
            if (!it) {
                // verify the dataArticles list contains the correct articles
                val expected = listOf(article1, article2)
                assertEquals(expected, viewModel.articlesUiState.value.dataArticles)
            }
        }
    }

    @Test
    fun `test loadArticlesPopularViewMode error`() = runTest {
        // Given
        val page = 1
        val expectedError = Constants.ERROR_LOAD_FAILED
        `when`(popularViewsUseCase.execute(page)).thenReturn(flow { throw Exception(expectedError) })

        // Then
        viewModel.loadArticlesPopularViewMode()

        // Result
        viewModel.articlesUiState.value.isLoading.value = true
        advanceTimeBy(500)
        viewModel.articlesUiState.value.throwError.value.let {
            assertEquals(expectedError, it)
        }
    }
}
