package com.example.femalepoint.presenation.screens

import android.util.Log
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable
fun MyOrderScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    // Fetching orders on composable launch
    LaunchedEffect(Unit) {
        viewModel.getAllOrders()
    }

    // Collecting state
    val listOfData by viewModel.getAllOrderState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Orders",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when {
            listOfData.isloading -> {
                LoadingBar()
            }

            listOfData.error.isNotEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("CHECK INTERNET", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }

            listOfData.data.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No orders found!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            else -> {
                // Adding Box around LazyColumn to make sure it expands properly
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().fillMaxHeight(),
                       // verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(listOfData.data) { order ->
                            EachOrderItemLook(
                                productImage = order.productImage,
                                productCategory = order.productCategory,
                                productName = order.productname,
                                price = order.productinitialprice.toInt(),
                                finalprice = order.productFinalPrice.toInt(),
                                description = order.productname,
                                noOfUnits = order.noOfproducts,
                                productId = order.productID,
                                navController = navController
                            )
                        }
                        item {
                            Box(modifier = Modifier.height(16.dp)){
                                Text("hello")
                            }
                        }

                    }

                }
            }
        }
    }
}

@Composable
fun EachOrderItemLook(
    productImage: String,
    productCategory: String,
    productName: String,
    price: Int,
    finalprice: Int,
    description: String,
    noOfUnits: Int,
    productId: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Uncomment and implement navigation when needed
                // navController.navigate(ORDERHISTORYDETAILS(...))
            }
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            AsyncImage(
                model = productImage,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details
            Column(modifier = Modifier.weight(1f)) {
                Text(text = productName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = productCategory,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Price: $$price",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Final Price: $$finalprice",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
