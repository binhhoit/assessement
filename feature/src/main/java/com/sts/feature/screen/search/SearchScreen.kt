package com.sts.feature.screen.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sts.feature.screen.search.component.SearchBody

@Composable
fun SearchScreen(
    modifier: Modifier,
    direction: (String) -> Unit
) {
    SearchBody(modifier = modifier, direction = direction)
}