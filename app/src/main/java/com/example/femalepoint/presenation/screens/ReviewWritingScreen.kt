package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.femalepoint.FiraSansFamily
import com.example.femalepoint.data.ReviewDetails
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun ReviewWritingAndUploadingScreen(
    viewModel: MyViewModel= hiltViewModel(),
    productID:String="",imageUrl:String,
    category:String="",productname:String=""

) {
    val context = LocalContext.current

    val review = remember {
        mutableStateOf("")
    }
    val UserName = remember {
        mutableStateOf("")
    }
    val reviewuploadstate = viewModel.reviewDetailsUpload.collectAsState()
    if (reviewuploadstate.value.isloading) {
        LoadingBar()

    } else if (reviewuploadstate.value.error.isNotEmpty()) {
        Text("SERVER SIDE ERROR")
    } else {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("WRITE ABOUT YOUR EXPERIENCE HERE :")
            OutlinedTextField(
                value = review.value,
                onValueChange = { review.value = it },
                placeholder = {
                    Text("Write here...")
                },
                modifier = Modifier.fillMaxWidth(0.85f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = UserName.value,
                onValueChange = { UserName.value = it },
                placeholder = {
                    Text("Name")
                },
                modifier = Modifier.fillMaxWidth(0.85f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    if (review.value.isNotEmpty() && UserName.value.isNotEmpty()) {
                        val reviewObject = ReviewDetails(
                            productID = productID, imageUrl = imageUrl,
                            review = review.value, rating = 0, category = category,
                            productName = productname, userName = UserName.value
                        )

                        viewModel.reviewDeatilsUpload(
                            productID = productID,
                            imageUrl = imageUrl, reviewDetails = reviewObject
                        )
                        FancyToast.makeText(
                            context, "Added Review Successfully",
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS, false
                        ).show()

                    } else {
                        FancyToast.makeText(
                            context, "Enter All Fields",
                            FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false
                        ).show()
                    }

                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFFFFAFB8), // Button background color
                    contentColor = Color.Black         // Text color
                )
            ) {
                Text(
                    text = "Upload Review",
                    fontFamily = FiraSansFamily
                )
            }

        }

    }
}
