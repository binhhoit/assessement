package com.sts.data.domain.article

import com.sts.data.domain.UseCase
import com.sts.data.local.SharePreferenceManager
import com.sts.data.model.response.ArticlesResponse
import com.sts.data.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PopularViewsUseCase(private val articleRepository: ArticleRepository,
                          private val localDataManager: SharePreferenceManager) :
    UseCase<Int, Flow<ArticlesResponse>>() {

    override fun execute(param: Int?) = flow {
        try {
            if (param == null)
                throw IOException()
            val data = articleRepository.getArticlesPopularView(period = param)
            if (data.results.isNotEmpty())
                localDataManager.saveArticlesData(data)
            emit(data)
        } catch (exception: IOException) {
            emit(localDataManager.getArticlesData())
        } catch (exception: HttpException) {
            emit(localDataManager.getArticlesData())
        } catch (e: Exception) {
            throw e
        }
    }

}