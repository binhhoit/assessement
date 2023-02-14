package com.sts.feature.screen.articels

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sts.feature.screen.articels.component.ArticlesBody
import com.sts.feature.utils.ModeArticle

@Composable
fun ArticlesScreen(modifier: Modifier,
                   viewModel: ArticlesViewModel,
                   mode: ModeArticle,
                   keyword: String?) {
    ArticlesBody(modifier = modifier, viewModel = viewModel, mode = mode, keyword = keyword)
}