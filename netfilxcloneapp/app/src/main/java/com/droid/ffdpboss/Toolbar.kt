package com.droid.ffdpboss


import androidx.compose.foundation.border
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    appBarColors: AppBarColors? // Custom colors
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.border(1.dp, color = Color.LightGray.copy(alpha = 0.6f)),
        title = { LargeText(modifier = Modifier, text=title, color = textColor) },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = primary,
                    contentDescription = "Back"
                )
            }
        }
    )

}

data class AppBarColors(
    val primary: Color,
    val onPrimary: Color
)