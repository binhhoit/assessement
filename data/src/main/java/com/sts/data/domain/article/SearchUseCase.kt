package com.sts.data.domain.article

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sts.data.model.Article
import com.sts.data.repository.ArticleRepository
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class SearchUseCase(private val keyword: String,
                    private val repo: ArticleRepository)
    : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val nextPage = params.key ?: 0
            val result = repo.getArticlesSearch(keyword = keyword, page = nextPage)
            Timber.e(result.response.docs.size.toString() + "  " + nextPage)
            LoadResult.Page(
                data = result.response.docs,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (result.response.docs.isEmpty()) null else nextPage + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}