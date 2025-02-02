package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.femalepoint.FiraSansFamily
import com.example.femalepoint.R
import com.example.femalepoint.navigation.ReviewWritingScreen
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel

//@Composable
//fun OrderHistoryScreen(viewModel: MyViewModel = hiltViewModel(), productImage: String,
//                       productCategory: String, productName: String,
//                       price: Int, finalprice: Int,
//                       description: String, noOfUnits: Int, productId: String, navController: NavController
//) {
//    val getAllOrdersState = viewModel.getAllOrderState.collectAsState()
//    if (getAllOrdersState.value.isloading) {
//        LoadingBar()
//    } else if (getAllOrdersState.value.error.isNotEmpty()) {
//        Text("ERROR FROM SERVER SIDE")
//
//    } else {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState()),
//            verticalArrangement = Arrangement.Top // Top alignment for a cleaner design
//        ) {
//            // Header
//            Text(
//                text = "Product Details",
//                fontFamily = FiraSansFamily,
//                color = Color.Black,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 24.dp, bottom = 16.dp),
//                fontSize = 24.sp // Larger font size for a prominent header
//            )
//
//            // Product name
//            Text(
//                text = productName,
//                fontFamily = FiraSansFamily,
//                fontSize = 20.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                color = Color.Gray
//            )
//
//            // Product image
//            AsyncImage(
//                model = productImage,
//                contentDescription = "Product Image",
//                placeholder = painterResource(R.drawable.loading),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 16.dp)
//                    .height(250.dp), // Maintain image size proportion
//                contentScale = ContentScale.Fit
//            )
//
//            // Price information in a row
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween // Space prices evenly
//            ) {
//                Text(
//                    text = "Price: $price",
//                    fontFamily = FiraSansFamily,
//                    color = Color.Gray,
//                    textDecoration = TextDecoration.LineThrough // Strike-through for the old price
//                )
//                Text(
//                    text = "Final Price: $finalprice",
//                    fontFamily = FiraSansFamily,
//                    color = Color(0xFF2ECC71), // Highlight discount price in green
//                    fontSize = 18.sp
//                )
//            }
//
//            // Units purchased
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Units Purchased: $noOfUnits",
//                fontFamily = FiraSansFamily,
//                fontSize = 16.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                color = Color.Black
//            )
//
//            // Description
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Category :$productCategory",
//                fontFamily = FiraSansFamily,
//                fontSize = 16.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                color = Color.Gray
//            )
//
//
//            // Spacer to separate content
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Review Order button
//            OutlinedButton(
//                onClick = {
//                    // TODO: Add your logic here
//                    navController.navigate(ReviewWritingScreen(
//                        productID = productId,
//                        imageUrl = productImage,
//                        category = productCategory,
//                        productname = productName
//                    ))
//                },
//                modifier = Modifier
//                    .fillMaxWidth(0.8f)
//                    .padding(horizontal = 16.dp), // Center the button
//                colors = ButtonDefaults.outlinedButtonColors(
//                    containerColor = Color(0xFFFFAFB8), // Light pink background
//                    contentColor = Color.Black         // Black text
//                )
//            ) {
//                Text(
//                    text = "REVIEW ORDER",
//                    fontFamily = FiraSansFamily,
//                    fontSize = 16.sp // Appropriate size for the button text
//                )
//
//            }
//        }
//
//    }
//}
@Composable
fun OrderHistoryScreen(
    viewModel: MyViewModel = hiltViewModel(),
    productImage: String,
    productCategory: String,
    productName: String,
    price: Int,
    finalPrice: Int,
    description: String,
    noOfUnits: Int,
    productId: String,
    navController: NavController
) {
    val getAllOrdersState = viewModel.getAllOrderState.collectAsState()

    when {
        getAllOrdersState.value.isloading -> {
            LoadingBar()
        }

        getAllOrdersState.value.error.isNotEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("ERROR FROM SERVER SIDE", color = Color.Red, fontSize = 18.sp)
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Order Details",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Product Image
                AsyncImage(
                    model = productImage,
                    contentDescription = "Product Image",
                    placeholder = painterResource(R.drawable.loading),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp)), // Rounded corners for a smooth look
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Product Name
                Text(
                    text = productName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Category
                Text(
                    text = "Category: $productCategory",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Price Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "₹$price",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        text = "₹$finalPrice",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2ECC71)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Units Purchased
                Text(
                    text = "Units Purchased: $noOfUnits",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Review Order Button
                Button(
                    onClick = {
                        navController.navigate(
                            ReviewWritingScreen(
                                productID = productId,
                                imageUrl = productImage,
                                category = productCategory,
                                productname = productName
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6F61), // Bright Coral for a standout button
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp) // Softer button edges
                ) {
                    Text(
                        text = "REVIEW ORDER",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
