package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.femalepoint.FiraSansFamily
import com.example.femalepoint.data.ReviewDetails
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun ReviewWritingAndUploadingScreen(
    viewModel: MyViewModel = hiltViewModel(),
    productID: String = "",
    imageUrl: String,
    category: String = "",
    productname: String = ""
) {
    val context = LocalContext.current
    val review = remember { mutableStateOf("") }
    val userName = remember { mutableStateOf("") }
    val reviewUploadState = viewModel.reviewDetailsUpload.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Write Your Review",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = review.value,
            onValueChange = { review.value = it },
            placeholder = { Text("Share your experience...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            placeholder = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (review.value.isNotEmpty() && userName.value.isNotEmpty()) {
                    val reviewObject = ReviewDetails(
                        productID = productID,
                        imageUrl = imageUrl,
                        review = review.value,
                        rating = 0,
                        category = category,
                        productName = productname,
                        userName = userName.value
                    )

                    viewModel.reviewDeatilsUpload(
                        productID = productID,
                        imageUrl = imageUrl,
                        reviewDetails = reviewObject
                    )

                    FancyToast.makeText(
                        context, "Review Added Successfully",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS, false
                    ).show()
                } else {
                    FancyToast.makeText(
                        context, "Please fill in all fields",
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING, false
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFAFB8), // Button background color
                contentColor = Color.Black // Text color
            )
        ) {
            Text(text = "Submit Review", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (reviewUploadState.value.isloading) {
            CircularProgressIndicator()
        } else if (reviewUploadState.value.error.isNotEmpty()) {
            Text("Server Error. Try Again!", color = Color.Red, fontWeight = FontWeight.Bold)
        }
    }
}
