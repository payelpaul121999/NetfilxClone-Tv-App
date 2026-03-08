package com.droid.ffdpboss

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.droid.ffdpboss.ui.theme.darkRed

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeLeftCard(modifier: Modifier,time : String){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_time), contentDescription = "Time", tint = darkRed)
        MediumText(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            text = "Close :- ${getTimeLeft(time)}",
            color = darkRed
        )
    }
}