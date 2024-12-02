package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable
fun AllReviewScreen(viewModel: MyViewModel = hiltViewModel(),productID:String) {
    LaunchedEffect(Unit) {
        viewModel.allrevivewDetails(productID = productID)
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
                items(allReviewDetailsState.value.data) { review ->
                    if (review.review.isNotEmpty()) {
                        ReviewCard(
                            userName = review.userName,
                            reviewText = review.review
                        )
                    } else {
                        NoReviewCard()
                    }
                }
            }
        }

    }
}
@Composable
fun ReviewCard(userName: String, reviewText: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Card takes full width of the parent
            .padding(8.dp), // Padding around the card
        elevation = cardElevation(8.dp), // Shadow effect for the card
        shape = RoundedCornerShape(8.dp) // Rounded corners
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp) // Padding inside the card
        ) {
            // Display the username
            Text(
                text = userName,
                style = MaterialTheme.typography.titleLarge,

            )
            Spacer(modifier = Modifier.height(8.dp)) // Space between username and review
            // Display the review
            Text(
                text = reviewText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface // Adjust text color to match the theme
            )
        }
    }
}
@Composable
fun NoReviewCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Center-align text
        ) {
            Text(
                text = "No review yet",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}