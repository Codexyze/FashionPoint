package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.femalepoint.R
import com.example.femalepoint.navigation.PROFILEOFDETAILSSCREEN
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable
fun AllReviewScreen(viewModel: MyViewModel = hiltViewModel(),productID:String,navController: NavController) {
    LaunchedEffect(Unit) {
        viewModel.allrevivewDetails(productID = productID)
       // viewModel.getProfilePictureAfterUpdate()
    }

    val allReviewDetailsState = viewModel.allReviewDetails.collectAsState()
    if (allReviewDetailsState.value.isloading) {
        LoadingBar()
    } else if (allReviewDetailsState.value.data.isEmpty()) {
        Text("No revivew yet")

    } else if (allReviewDetailsState.value.error.isNotEmpty()) {
        Text("SERVER ERROR")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Padding for overall layout
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), // LazyColumn will fill the screen
                verticalArrangement = Arrangement.spacedBy(8.dp) // Add spacing between items
            ) {
                // Iterating over the list of reviews
                items(allReviewDetailsState.value.data) {review ->
                    if (review.review.isNotEmpty()) {
                        ReviewCard(
                            userName = review.userName,
                            reviewText = review.review,
                            userID = review.userID,
                            navController = navController,
                            review = review.rating
                        )
                    } else {
                        NoReviewScreen()
                    }
                }
            }
        }

    }
}
@Composable
fun ReviewCard(userName: String, reviewText: String,userID:String,
               viewmodel:MyViewModel= hiltViewModel(),
               navController: NavController,review:Int=0) {
    LaunchedEffect(Unit) {
        viewmodel.getProfilePictureByUserID(userID = userID)

    }
    val getProfilePictureByUserIDState=viewmodel.getProfilePictureByUserIDState.collectAsState()

    if(getProfilePictureByUserIDState.value.isloading){
        LoadingBar()
    }else if(getProfilePictureByUserIDState.value.error.isNotEmpty()){
        ErrorScreen()

    }else {
       // getProfilePictureByUserIDState.value.data?.imageUri |this brings image url

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = cardElevation(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically // Align items in the center vertically
                    , modifier = Modifier.clickable {
                      navController.navigate(PROFILEOFDETAILSSCREEN(userID = userID))

                    }
                ) {
                    AsyncImage(
                        model = getProfilePictureByUserIDState.value.data?.imageUri,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(40.dp) // Small size but visible
                            .clip(CircleShape) // Circular shape
                            .background(Color.Gray), // Placeholder background
                        contentScale = ContentScale.Crop // Crop to fit
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Space between image and name

                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = reviewText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (review ==0){
//                    Text("No rating yet")
//                    NoReviewScreen()
                }else{
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = {

                        }) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null,
                                tint = if(review>=1){
                                    Color.Yellow
                                }else{
                                    Color.Gray
                                })
                        }
                        IconButton(onClick = {

                        }) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null,
                                tint = if(review>=2){
                                    Color.Yellow
                                }else{
                                    Color.Gray
                                }
                            )
                        }
                        IconButton(onClick = {


                        }) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null,
                                tint = if(review>=3){
                                    Color.Yellow
                                }else{
                                    Color.Gray
                                })
                        }
                        IconButton(onClick = {

                        }) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null,
                                tint = if(review>=4){
                                    Color.Yellow
                                }else{
                                    Color.Gray
                                })
                        }
                        IconButton(onClick = {


                        }) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null,
                                tint = if(review>=5){
                                    Color.Yellow
                                }else{
                                    Color.Gray
                                })
                        }
                    }
                }

            }
        }

    }
}

@Composable
fun NoReviewScreen() {
  Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
      Image(painter = painterResource(R.drawable.notfound), contentDescription = "Notfound",
          modifier =Modifier.size(400.dp) )

  }
}
