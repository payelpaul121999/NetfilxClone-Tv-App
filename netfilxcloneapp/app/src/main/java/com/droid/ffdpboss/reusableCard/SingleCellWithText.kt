package com.droid.ffdpboss.reusableCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droid.data.model.betModel.BettingDraftList
import com.droid.ffdpboss.ui.theme.Purple40


@Composable
fun SingleCellWithText(bettingDraftList: BettingDraftList, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(5.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = modifier
                .height(50.dp)
                .weight(1f)
                .border(
                    BorderStroke(width = 1.dp, color = Purple40)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = bettingDraftList.betDigit.orEmpty(),
                fontSize = 14.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        Box(
            modifier = modifier
                .height(50.dp)
                .weight(1f)
                .border(
                    BorderStroke(width = 1.dp, color = Purple40)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = bettingDraftList.betAmount.orEmpty(),
                fontSize = 14.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        Box(
            modifier = modifier
                .height(50.dp)
                .weight(1f)
                .border(
                    BorderStroke(width = 1.dp, color = Purple40)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = bettingDraftList.action.orEmpty(),
                fontSize = 14.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }

}