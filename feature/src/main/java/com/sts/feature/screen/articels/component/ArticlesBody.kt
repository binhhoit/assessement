package com.sts.feature.screen.articels.component

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sts.data.model.Article
import com.sts.feature.R
import com.sts.feature.ext.styleText
import com.sts.feature.screen.articels.ArticlesViewModel
import com.sts.feature.utils.Constants
import com.sts.feature.utils.ModeArticle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticlesBody(
    modifier: Modifier,
    viewModel: ArticlesViewModel,
    mode: ModeArticle,
    keyword: String?,
) {
    val uiState = viewModel.articlesUiState.value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (mode.name) {
            ModeArticle.Search.name -> {
                LaunchedEffect(key1 = Unit) {
                    viewModel.setKeywordSearch(keyword = keyword ?: "")
                }
                LoadArticleForSearch(viewModel)
            }
            else -> {
                if (uiState.dataArticles.isNotEmpty()) {
                    LazyColumn(
                        modifier = modifier
                            .padding(top = 87.dp)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(10.dp),
                    ) {
                        items(
                            uiState.dataArticles.toList(),
                            key = { id -> "$id" },
                        ) { itemData ->
                            ItemArticle(
                                item = itemData,
                                modifier = Modifier.animateItemPlacement(),
                            )
                        }
                    }
                } else if (uiState.isLoading.value) {
                    CircularProgressIndicator()
                } else if (uiState.throwError.value.isNotEmpty()) {
                    Toast.makeText(
                        LocalContext.current,
                        uiState.throwError.value,
                        Toast.LENGTH_LONG,
                    ).show()
                }
                LaunchedEffect(key1 = Unit) {
                    viewModel.loadArticlesPopularViewMode()
                }
            }
        }
    }
}

@Composable
fun ItemArticle(
    item: Article,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = item.title ?: item.abstract ?: "",
                    style = styleText(),
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = stringResource(R.string.public_date) +
                            " ${item.publishedDate ?: item.pubDate ?: ""}",
                    style = styleText(),
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoadArticleForSearch(viewModel: ArticlesViewModel) {
    val swipeRefreshState = rememberSwipeRefreshState(false)

    val articlesPaging = viewModel.loadArticlesSearch.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp),
        contentAlignment = Alignment.Center,
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                articlesPaging.refresh()
                swipeRefreshState.isRefreshing = true
            },
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 5.dp,
                    top = 10.dp,
                    end = 5.dp,
                    bottom = 5.dp,
                ),
            ) {
                items(
                    items = articlesPaging,
                    key = { item -> item._id ?: "" },
                ) { item ->
                    swipeRefreshState.isRefreshing = false
                    if (item != null) {
                        ItemArticle(
                            item = item,
                            modifier = Modifier.animateItemPlacement(),
                        )
                    }
                }
                item {
                    if (articlesPaging.loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        if (articlesPaging.loadState.refresh is LoadState.Loading || articlesPaging.itemCount == 0) {
            CircularProgressIndicator()
        }
    }

    articlesPaging.apply {
        if (loadState.append is LoadState.Error) {
            Toast.makeText(
                LocalContext.current,
                Constants.ERROR_LOAD_FAILED,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}
