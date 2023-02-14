package com.sts.feature.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sts.feature.screen.home.component.BodyHome
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel,
    direction: (String) -> Unit
) {
    BodyHome(modifier = modifier, viewModel = viewModel, direction = direction)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { _ ->
        HomeScreen(modifier = Modifier.padding(), viewModel = koinViewModel()) { }
    }
}
