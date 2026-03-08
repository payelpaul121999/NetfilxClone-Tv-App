package com.droid.ffdpboss.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.droid.ffdpboss.R

// Set of Material typography styles to start with
val fonts: FontFamily = FontFamily(
    Font(R.font.font_regular, FontWeight.Normal),
    Font(R.font.font_medium, FontWeight.Medium),
    Font(R.font.font_semi_bold, FontWeight.SemiBold),
    Font(R.font.font_bold, FontWeight.Bold)
)
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = fonts, fontWeight = FontWeight.Bold, fontSize = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = fonts, fontWeight = FontWeight.SemiBold, fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fonts, fontWeight = FontWeight.Medium, fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fonts, fontWeight = FontWeight.Normal, fontSize = 12.sp
    ),

    )