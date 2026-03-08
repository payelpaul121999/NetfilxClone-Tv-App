package com.droid.ffdpboss.auth

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
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
import com.droid.ffdpboss.home.HomeActivity
import com.droid.ffdpboss.ui.theme.primary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController,viewModel: AuthViewModel = getViewModel()){
    var fullNameText by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var conPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFFFFF)) {
        Column(modifier = Modifier.padding(20.dp, 10.dp)) {
            Spacer(modifier = Modifier.height(28.dp))
            Image(
                painterResource(R.drawable.baseline_arrow_back),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(35.dp)
                    .clickable {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Create Account", color = Color(0xFF01030E),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(
                        BorderStroke(2.dp, color = primary),
                        shape = RoundedCornerShape(8.dp),
                    ),
                value = fullNameText,
                onValueChange = {
                    fullNameText = it
                },
                trailingIcon = {
                    Icon(Icons.Filled.AccountBox, "", tint = primary)
                },
                label = {
                    Text("Full Name")
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),

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
                colors = TextFieldDefaults.textFieldColors(
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
                value = password,
                onValueChange = {
                    password = it
                },
                singleLine = true,
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        R.drawable.baseline_visibility_on
                    else  R.drawable.baseline_visibility_off
                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        // Icon(imageVector = image, description)
                        Icon(painterResource(id =image), description)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
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
                label = { Text("Confirm Password") },
                singleLine = true,
                placeholder = { Text("Confirm Password") },
                visualTransformation = if (conPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (conPasswordVisible)
                        R.drawable.baseline_visibility_on
                    else  R.drawable.baseline_visibility_off
                    // Please provide localized description for accessibility services
                    val description = if (conPasswordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { conPasswordVisible = !conPasswordVisible }) {
                        // Icon(imageVector = image, description)
                        Icon(painterResource(id =image), description)
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
                    if (isValidMobileNumber(phoneNumber) && confirmPassword==password && fullNameText.isNotEmpty()) {
                        scope.launch {
                            viewModel.onRegister(
                                fullNameText,
                                phoneNumber,
                                phoneNumber,
                                password,
                                confirmPassword
                            ).collect {
                                if (it?.Status == true) {
                                    (context as AuthActivity).finish()
                                    context.startActivity(
                                        Intent(context,
                                            HomeActivity::class.java)
                                    )
                                }else{
                                    showToast(context,it?.Message ?: "Something Went Wrong")
                                }
                            }
                        }
                    }else if(fullNameText.isEmpty()){
                        showToast(context,"Please enter full name")
                    }else if(confirmPassword.isEmpty()){
                        showToast(context,"Please enter confirm password")
                    }else{
                        showToast(context,"Please enter vaild mobile number.")
                    }

                }) {
                Text(
                    text = "Sign Up",
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
                    text = "Already have an account?", color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Sign in", color = Color(0xFF6650a4),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

