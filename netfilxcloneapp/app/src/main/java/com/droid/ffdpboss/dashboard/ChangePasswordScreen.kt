package com.droid.ffdpboss.dashboard

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.data.isValidMobileNumber
import com.droid.data.model.authModel.ChangePasswordRequest
import com.droid.data.showToast
import com.droid.ffdpboss.R
import com.droid.ffdpboss.auth.AuthActivity
import com.droid.ffdpboss.auth.AuthViewModel
import com.droid.ffdpboss.auth.navigation.AuthScreen
import com.droid.ffdpboss.data.DataPreferences
import com.droid.ffdpboss.home.HomeActivity
import com.droid.ffdpboss.ui.theme.primary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(navController: NavController, viewModel: AuthViewModel = getViewModel()) {
   // var confirmPassword by rememberSaveable { mutableStateOf("") }
    var oldPasswordVisible by rememberSaveable { mutableStateOf(true) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(true) }
    var conPasswordVisible by rememberSaveable { mutableStateOf(true) }
    var NewPhoneNumber by rememberSaveable { mutableStateOf("") }
    var ConPhoneNumber by rememberSaveable { mutableStateOf("") }
    var oldPasswordNumber by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFFFFF)
    ) {
        Column(
            modifier = Modifier.padding(20.dp, 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            //Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Change Password", color = Color(0xFF01030E),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            /*Text(
                text = "Please sign in to continue", color = primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )*/
            Spacer(modifier = Modifier.height(14.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        BorderStroke(2.dp, color = primary),
                        shape = RoundedCornerShape(8.dp),
                    ),
                value = oldPasswordNumber,
                onValueChange = {
                    oldPasswordNumber = it
                },

                trailingIcon = {
                    val image = if (oldPasswordVisible)
                        R.drawable.baseline_visibility_on
                    else R.drawable.baseline_visibility_off
                    // Please provide localized description for accessibility services
                    val description = if (oldPasswordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { oldPasswordVisible = !oldPasswordVisible }) {
                        // Icon(imageVector = image, description)
                        Icon(painterResource(id = image), description)
                    }
                },
                visualTransformation = if (oldPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                label = { Text("Old Password") },
                colors = TextFieldDefaults.colors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )

            )
            Spacer(modifier = Modifier.height(14.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        BorderStroke(2.dp, color = primary),
                        shape = RoundedCornerShape(8.dp),
                    ),
                value = NewPhoneNumber,
                onValueChange = { NewPhoneNumber = it },
                label = { Text("New Password") },
                singleLine = true,
                placeholder = { Text("New Password") },
                visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (newPasswordVisible)
                        R.drawable.baseline_visibility_on
                    else R.drawable.baseline_visibility_off
                    // Please provide localized description for accessibility services
                    val description = if (newPasswordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                        // Icon(imageVector = image, description)
                        Icon(painterResource(id = image), description)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        BorderStroke(2.dp, color = primary),
                        shape = RoundedCornerShape(8.dp),
                    ),
                value = ConPhoneNumber,
                onValueChange = { ConPhoneNumber = it },
                label = { Text("Confirm Password") },
                singleLine = true,
                placeholder = { Text("Confirm Password") },
                visualTransformation = if (conPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (conPasswordVisible)
                        R.drawable.baseline_visibility_on
                    else R.drawable.baseline_visibility_off
                    // Please provide localized description for accessibility services
                    val description = if (conPasswordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { conPasswordVisible = !conPasswordVisible }) {
                        // Icon(imageVector = image, description)
                        Icon(painterResource(id = image), description)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                onClick = {
                    if (oldPasswordNumber.isNotEmpty() &&  ConPhoneNumber.isNotEmpty() && NewPhoneNumber.isNotEmpty() &&(NewPhoneNumber==ConPhoneNumber)) {
                        scope.launch {
                            Log.d("####@@@","$oldPasswordNumber $ConPhoneNumber $NewPhoneNumber")
                            viewModel.onChangePassword(ChangePasswordRequest(DataPreferences(context).getApiKey().orEmpty(),NewPhoneNumber.orEmpty(),ConPhoneNumber.orEmpty(),oldPasswordNumber.orEmpty())).collect {
                                if (it?.Status == true) {
                                    navController.popBackStack()
                                    showToast(context, it?.Message!!)
                                } else {
                                    showToast(context, it?.Message!!)
                                }
                            }
                        }
                    } else if (oldPasswordNumber.isEmpty() || ConPhoneNumber.isEmpty() || ConPhoneNumber.isEmpty()) {
                        showToast(context, "Please enter password")
                    }else if(NewPhoneNumber!=ConPhoneNumber){
                        showToast(context, "New password and confirm password mismatch.")
                    }else {
                        showToast(context, "Please enter password")
                    }

                }) {
                Text(
                    text = "Change Password",
                    fontSize = 14.sp
                )
            }
        }
    }
}