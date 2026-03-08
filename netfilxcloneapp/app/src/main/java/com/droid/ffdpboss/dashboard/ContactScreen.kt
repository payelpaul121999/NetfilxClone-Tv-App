package com.droid.ffdpboss.dashboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.R
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.tertiary
import org.koin.androidx.compose.getViewModel

@Composable
fun ContactScreen(navController: NavController, viewModel: HomeViewModel = getViewModel()) {
    var hasCallPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val contactInfo = remember {
        viewModel.contactInfo
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(


        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCallPermission = isGranted
        if (!isGranted) {
            Toast.makeText(context, "Permission DENIED", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchContactInfo()
    }

    SideDrawer(navController = navController, screen = HomeScreen.ContactUsScreen, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .background(color = tertiary)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = primary),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LargeText(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Contact Support",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Image(
                painterResource(R.drawable.support_ill),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable {
                        // navController.popBackStack()
                    }
            )
            LargeText(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                text = "We are happy to help you",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            LargeText(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                text = "Just give us a call or whatsapp your query\nOur team will resolve your query as on priority.",
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .padding(top = 30.dp, bottom = 30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painterResource(R.drawable.con_ic),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .background(color = tertiary)
                        .size(50.dp)
                        .clickable {
                            val phoneNumber = contactInfo.value?.mobileNo
                            val callIntent = Intent(Intent.ACTION_CALL)
                            callIntent.data = Uri.parse("tel:$phoneNumber")
                            context.startActivity(callIntent)
                            /* if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                             } else {
                                 requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                             }*/
                        }
                )
                Image(
                    painterResource(R.drawable.whatsapp_ic),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            val uri =
                                Uri.parse("https://api.whatsapp.com/send?phone=${contactInfo.value?.whatsAppNo}&text=Play Game Now")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.setPackage("com.whatsapp")
                            if (intent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(intent)
                            } else {
                                // Handle the case where WhatsApp is not installed
                            }
                        }
                )
            }
        }
    })
    fun makePhoneCall(context: Context) {
        val phoneNumber = "78908097147"
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(callIntent)
    }

}