package com.droid.ffdpboss.auth

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.data.isValidMobileNumber
import com.droid.data.showToast
import com.droid.ffdpboss.R
import com.droid.ffdpboss.auth.navigation.AuthScreen
import com.droid.ffdpboss.home.HomeActivity
import com.droid.ffdpboss.ui.theme.primary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel = getViewModel()) {
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var conPasswordVisible by rememberSaveable { mutableStateOf(true) }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
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
                text = "Login", color = Color(0xFF01030E),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Please sign in to continue", color = primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
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
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                },

                trailingIcon = {
                    Icon(Icons.Filled.Phone, "", tint = primary)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                label = { Text("Mobile Number") },
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
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Password") },
                singleLine = true,
                placeholder = { Text("Password") },
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
                    if (isValidMobileNumber(phoneNumber) && confirmPassword.isNotEmpty()) {
                        scope.launch {
                            viewModel.onLogin(phoneNumber, confirmPassword).collect {
                                if (it?.Status == true) {
                                    (context as AuthActivity).finish()
                                    context.startActivity(Intent(context, HomeActivity::class.java))
                                } else {
                                    showToast(context, it?.Message!!)
                                }
                            }
                        }
                    } else if (confirmPassword.isEmpty()) {
                        showToast(context, "Please enter password")
                    } else {
                        showToast(context, "Please enter vaild mobile number.")
                    }

                }) {
                Text(
                    text = "Login",
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account?", color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Sign up", color = Color(0xFF6650a4),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(AuthScreen.SignUpScreen.route)
                    }
                )
            }
        }
    }
}