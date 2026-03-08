package com.droid.ffdpboss.data

import androidx.compose.ui.graphics.Color

data class GameListData(
    val borderColor : Color,
    val boxColor : Color,
    val gameHeadLine :String,
    val gameDescption : String,
    val actviteTime :String ="12.00",
    val deActviteTime :String ="23.00"
)
