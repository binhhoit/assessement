package com.sts.feature.ext

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


fun styleText(fontSize: TextUnit = 13.sp) = TextStyle(
    fontWeight = FontWeight.Normal,
    fontFamily = FontFamily.SansSerif,
    fontSize = fontSize
)

fun styleTextMedium(fontSize: TextUnit = 13.sp) = TextStyle(
    fontWeight = FontWeight.Medium,
    fontFamily = FontFamily.SansSerif,
    fontSize = fontSize
)

fun styleTextBold(fontSize: TextUnit = 13.sp) = TextStyle(
    fontWeight = FontWeight.Bold,
    fontFamily = FontFamily.SansSerif,
    fontSize = fontSize
)

fun styleTextSemiBold(fontSize: TextUnit = 13.sp) = TextStyle(
    fontWeight = FontWeight.SemiBold,
    fontFamily = FontFamily.SansSerif,
    fontSize = fontSize
)

fun styleTextLight(fontSize: TextUnit = 13.sp) = TextStyle(
    fontWeight = FontWeight.Light,
    fontFamily = FontFamily.SansSerif,
    fontSize = fontSize
)