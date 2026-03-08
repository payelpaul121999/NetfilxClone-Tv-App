package com.droid.ffdpboss

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.droid.ffdpboss.ui.theme.textColor


@Composable
fun NormalText(
    modifier: Modifier,
    fontSize: TextUnit = 8.sp,
    singleLine: Boolean = false,
    text: String,
    color: Color = textColor,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
        style = MaterialTheme.typography.titleSmall,
        fontSize = fontSize,
        textAlign = textAlign
    )
}

@Composable
fun MediumText(
    modifier: Modifier,
    fontSize: TextUnit = 16.sp,
    text: String,
    singleLine: Boolean = false,
    color: Color = textColor,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        fontSize = fontSize,
        textAlign = textAlign,
        maxLines = if (singleLine) 1 else Int.MAX_VALUE,
    )
}

@Composable
fun LargeText(
    modifier: Modifier,
    fontSize: TextUnit = 20.sp,
    text: String,
    color: Color = textColor,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.labelLarge,
        fontSize = fontSize,
        textAlign = textAlign
    )
}

@Composable
fun LargeBoldText(
    modifier: Modifier,
    fontSize: TextUnit = 22.sp,
    text: String,
    color: Color = textColor,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        fontSize = fontSize,
        textAlign = textAlign
    )
}