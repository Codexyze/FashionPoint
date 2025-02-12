package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import com.example.femalepoint.navigation.ORDERHISTORYDETAILS
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable
fun ExpenseTrackingScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    // Fetch orders when the screen launches
    LaunchedEffect(Unit) {
        viewModel.getAllOrders()
    }

    // Collecting state
    val listOfData by viewModel.getAllOrderState.collectAsState()

    if (listOfData.isloading) {
        LoadingBar()
    } else if (listOfData.error.isNotEmpty()) {
        ErrorScreen()
    } else {
        // Calculate the total expense before composition
        val totalExpense = listOfData.data.sumOf { it.productFinalPrice.toInt() * it.noOfproducts.toInt() }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Text("Total Expense: $totalExpense")
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(listOfData.data) { order ->
                        EachExpenseItemLook(
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
                        Box(modifier = Modifier.height(16.dp)) {
                            Text("hello")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EachExpenseItemLook(
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
                navController.navigate(
                    ORDERHISTORYDETAILS(productCategory=productCategory,
                    productName=productName,
                    price = price,
                    finalprice = finalprice,
                    description = description,
                    noOfUnits = noOfUnits,
                    productId = productId,
                    imageUri = productImage
                )
                )
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

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Units : $noOfUnits",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = " Price: Rs $finalprice",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
