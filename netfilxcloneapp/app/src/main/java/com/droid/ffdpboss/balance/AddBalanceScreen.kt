package com.droid.ffdpboss.balance

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.data.isValidAmount
import com.droid.data.isValidMobileNumber
import com.droid.data.model.balance.AddBalanceRequestBody
import com.droid.data.model.balance.model.PaymentTypeModel
import com.droid.data.showToast
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.data.DataPreferences
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.secondary
import com.droid.ffdpboss.ui.theme.tertiary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBalanceScreen(
    navController: NavController,
    screenName: String,
    viewModel: HomeViewModel = getViewModel(),
    screenType: Int?
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var amountText by rememberSaveable { mutableStateOf("") }
    val listRadio: List<PaymentTypeModel> = listOf(
        PaymentTypeModel("GooglePay", "1"),
        PaymentTypeModel("PhonePay", "2"),
        PaymentTypeModel("Bhim", "3"),
        PaymentTypeModel("Bank", "4")
    )

    var selectedItemBankName by remember { mutableStateOf<String?>(null) }

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
                    .fillMaxSize()
                    .background(color = tertiary)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = primary),
                    contentAlignment = Alignment.Center
                ) {
                    LargeText(
                        modifier = Modifier,
                        text = screenName,
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

                LabelWithTextField(label = "Mobile", hint = "Enter Mobile Number") {
                    phoneNumber = it
                }
                LabelWithTextField(label = "Amount", hint = "Enter your Amount") {
                    amountText = it
                }

                SingleSelectListWithRadio(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    listRadio
                ) {
                    selectedItemBankName = it
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = secondary,
                    ), onClick = {
                        val apiKey = DataPreferences(context).getApiKey()
                        if (isValidMobileNumber(phoneNumber) && isValidAmount(amountText) && !selectedItemBankName.isNullOrBlank()) {
                            scope.launch {
                                if (screenType == 0) {
                                    viewModel.onAddBalance(
                                        AddBalanceRequestBody(
                                            apiKey,
                                            phoneNumber, selectedItemBankName, amountText
                                        )
                                    ).collect {
                                        if (it?.Status == true) {
                                            showToast(context, it?.Message!!)
                                            navController.popBackStack()
                                        } else {
                                            showToast(context, it?.Message!!)
                                        }
                                    }
                                } else {
                                    viewModel.onWithDrawralBalance(
                                        AddBalanceRequestBody(
                                            apiKey,
                                            phoneNumber, selectedItemBankName, amountText
                                        )
                                    ).collect {
                                        if (it?.Status == true) {
                                            showToast(context, it?.Message!!)
                                            navController.popBackStack()
                                        } else {
                                            showToast(context, it?.Message!!)
                                        }
                                    }
                                }
                            }
                        } else if (phoneNumber.isEmpty()) {
                            showToast(context, "Please Mobile number")
                        } else if (selectedItemBankName.isNullOrBlank()) {
                            showToast(context, "Please Select Payment Type")
                        } else if (isValidAmount(amountText)) {
                            showToast(context, "Please enter valid Amount")
                        } else {
                            showToast(context, "Please enter valid detail")
                        }
                    }) {
                    MediumText(
                        text = "Submit Request",
                        modifier = Modifier.padding(2.dp),
                        color = Color.White
                    )
                }
            }


        }

    })

}

@Composable
private fun LabelWithTextField(label: String, hint: String, onInput: (String) -> Unit) {
    val item = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        MediumText(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = label,
            color = Color.White
        )
        TextField(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 4.dp)
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
            value = item.value,
            onValueChange = {
                onInput(it)
                item.value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { MediumText(modifier = Modifier, text = hint, color = Color.LightGray) },
            singleLine = true,
            maxLines = 1

        )
    }
}

@Composable
fun SingleSelectListWithRadio(
    modifier: Modifier = Modifier,
    items: List<PaymentTypeModel>,
    onClickRadio: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf<PaymentTypeModel?>(null) }

    LazyColumn(modifier = modifier) {
        items(items) { item ->
            val isSelected = item == selectedItem
            Row(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
                    .clickable(onClick = {
                        selectedItem = item
                        onClickRadio(selectedItem?.number!!)
                    })
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = {
                        selectedItem = item
                        onClickRadio(selectedItem?.number!!)
                    },
                    modifier = Modifier.padding(end = 10.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = secondary, unselectedColor = Color.LightGray
                    )
                )
                MediumText(text = item.paymentType, modifier = Modifier, color = Color.White)
            }
        }
    }
}