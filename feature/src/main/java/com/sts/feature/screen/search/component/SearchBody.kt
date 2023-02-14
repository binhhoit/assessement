package com.sts.feature.screen.search.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sts.feature.R
import com.sts.feature.ext.styleText
import com.sts.feature.navigation.Route
import com.sts.feature.utils.ModeArticle


@Composable
fun SearchBody(modifier: Modifier,
               direction: (String) -> Unit) {

    val inputSearchKey = remember { mutableStateOf("") }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(top = 187.dp)
        .padding(horizontal = 15.dp)) {
        SearchBar(modifier = Modifier, inputKey = inputSearchKey)
        ButtonSearch(modifier = Modifier, inputKey = inputSearchKey) {
            direction.invoke(
                Route.Articles.route
                    .replaceFirst("{mode}", ModeArticle.Search.name)
                    .replaceFirst("{keyword}", inputSearchKey.value))
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier, inputKey: MutableState<String>) {
    Column(modifier) {
        OutlinedTextField(
            value = inputKey.value,
            onValueChange = { inputKey.value = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            placeholder = {
                Text(
                    text = "Search product here",
                    fontSize = 13.sp,
                    style = styleText()
                )
            },
            textStyle = styleText(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
        )
    }
}


@Composable
fun ButtonSearch(modifier: Modifier, inputKey: MutableState<String>, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 45.dp),
        onClick = onClick,
        enabled = inputKey.value.isNotBlank(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = Color.White,
        )
    ) {
        Text(text = stringResource(id = R.string.search), style = styleText(15.sp))
    }
}
