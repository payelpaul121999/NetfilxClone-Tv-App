package com.droid.netfilxclone.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object NetflixColors {
    val FocusedBorder = Color(0xFFFFFFFF)  // ← plain white
    // Theme.kt → object NetflixColors
  val White          = Color(0xFFFFFFFF)
  val Black          = Color(0xFF000000)
  val  LightGray      = Color(0xFF808080)
  val  TextSecondary  = Color(0xFFB3B3B3)
  val TextPrimary    = Color(0xFFFFFFFF)
  val RedDark    = Color(0xFFD50D0D)
  val Red    = Color(0xFFD50D0D)
  val Gold    = Color(0xFFFF9800)
  val TextTertiary    = Color(0xFF131110)
}

object NetflixDimensions {
    val focusBorderWidth = 3.dp            // ← border stroke width
    val cornerRadius     = 4.dp            // ← used inside RoundedCornerShape(...)
    val cardWidth  = 200.dp
    val cardHeight = 120.dp
    val thumbnailWidth = 10.dp
    val thumbnailHeight = 10.dp
    val heroHeight = 500.dp
    val paddingXxl = 11.dp

}