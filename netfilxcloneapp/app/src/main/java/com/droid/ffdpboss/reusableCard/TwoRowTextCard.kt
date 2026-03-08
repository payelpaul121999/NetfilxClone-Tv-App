package com.droid.ffdpboss.reusableCard

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TwoRowText(title:String){
    Row {
        Text(
            text = title, color = Color(0xFF01030E),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "[Required]", color = Color(0xFF8B0419),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}