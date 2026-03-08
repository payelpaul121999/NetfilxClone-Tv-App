package com.droid.ffdpboss.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.data.model.homeModel.TransactionListItem
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.NormalText
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.tertiary
import com.droid.ffdpboss.ui.theme.textColor
import org.koin.androidx.compose.getViewModel

@Composable
fun TransactionListScreen(navController: NavController, viewModel: HomeViewModel = getViewModel()) {
    val transactionList = remember {
        viewModel.transactionList
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchTransactionList()
    }

    SideDrawer(navController = navController, screen = HomeScreen.ContactUsScreen, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .background(color = primary)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = primary),
                    contentAlignment = Alignment.Center
                ) {
                    LargeText(
                        modifier = Modifier,
                        text = "TRANSACTION HISTORY",
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = Color.White,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            }

            TransactionHeaderItem()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(transactionList) { transactionItem ->
                    TransactionItem(transactionItem)
                }
            }
        }

    })
}

@Composable
private fun TransactionHeaderItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(color = tertiary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MediumText(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .padding(vertical = 4.dp),
            text = "Pay Date",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
        MediumText(
            modifier = Modifier.weight(1f),
            text = "Payment Mode",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
        MediumText(
            modifier = Modifier.weight(1f),
            text = "Amount",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
        MediumText(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .padding(vertical = 4.dp),
            text = "Remarks",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TransactionItem(transactionListItem: TransactionListItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NormalText(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .padding(vertical = 4.dp),
            text = transactionListItem.payDate.orEmpty(),
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
        NormalText(
            modifier = Modifier.weight(1f),
            text = transactionListItem.paymentMode.orEmpty(),
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
        NormalText(
            modifier = Modifier.weight(1f),
            text = "$${transactionListItem.amount}",
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
        NormalText(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .padding(vertical = 4.dp),
            text = transactionListItem.remark.orEmpty(),
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}