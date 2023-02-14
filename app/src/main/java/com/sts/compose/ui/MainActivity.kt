package com.sts.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sts.compose.ui.components.AppCompose
import com.sts.compose.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCompose()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun DefaultPreview() {
    AppTheme {
        AppCompose()
    }
}
