package com.droid.ffdpboss.reusableCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.NormalText
import com.droid.ffdpboss.R
import com.droid.ffdpboss.ui.theme.primary

@Composable
fun ProfileCard(userName: String, mobileNumber: String, onClickBackPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = primary),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(R.drawable.profile_icon),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 12.dp)
                .size(48.dp)
        )
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 10.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            MediumText(
                modifier = Modifier,
                text = userName, color = Color.White,
                singleLine = true
            )
            NormalText(
                modifier = Modifier.padding(top = 2.dp),
                text = "Mobile no: $mobileNumber", color = Color.White,
                singleLine = true
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            IconButton(onClick = { onClickBackPressed() }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_close),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }


    }
}