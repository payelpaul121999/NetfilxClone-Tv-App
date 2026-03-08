package com.droid.ffdpboss.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droid.ffdpboss.LargeBoldText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.R
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickActionAlertDialog(onDismiss: () -> Unit, onNavigate: (String) -> Unit) {
    androidx.compose.material3.AlertDialog(onDismissRequest = {
        onDismiss()
    }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                LargeBoldText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    text = "Quick Menu",
                    color = secondary
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(quickNavigationItems) {
                        MediumText(
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .fillMaxWidth()
                                .clickable { onNavigate(it.route) }, text = it.title
                        )
                    }
                }
            }
        }

    }


}

private val quickNavigationItems = listOf(
    NavigationItem(
        title = "ADD MONEY",
        HomeScreen.AddBalanceScreen.withArgs("Add_Balance"),
        R.drawable.add_balance/* selectedIcon = Icons.Filled.Info,
         unselectedIcon = Icons.Outlined.Info,*/
    ), NavigationItem(
        title = "WITHDRAW WINNING",
        HomeScreen.AddBalanceScreen.withArgs("Withdrawal"),
        R.drawable.withdraw_bal/* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ), NavigationItem(
        title = "Transaction History",
        HomeScreen.TransactionListScreen.route,
        R.drawable.transaction_bal/* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ), NavigationItem(
        title = "GAME RULES", HomeScreen.GameRulesScreen.route, R.drawable.rules_icon/* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ), NavigationItem(
        title = "Support", "", R.drawable.support_icon/* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ), NavigationItem(
        title = "SHARE APP", "share", R.drawable.share/* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    )
)
