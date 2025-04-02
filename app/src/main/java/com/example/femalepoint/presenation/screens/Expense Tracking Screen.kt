package com.example.femalepoint.presenation.screens

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
import com.example.femalepoint.presenation.navigation.ORDERHISTORYDETAILS
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import java.util.Calendar



@Composable
fun ExpenseTrackingScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    // Fetch orders when the screen launches
    LaunchedEffect(Unit) {
        viewModel.getAllOrders()
    }

    // Collecting state
    val listOfData by viewModel.getAllOrderState.collectAsState()
    val totalExpenseLifeTime = listOfData.data.sumOf { it.productFinalPrice.toInt() * it.noOfproducts.toInt() }

    when {
        listOfData.isloading -> {
            LoadingBar()
        }
        listOfData.error.isNotEmpty() -> {
            ErrorScreen()
        }
        else -> {

            // Get an instance of the Calendar class representing the current date and time
            val calendar = Calendar.getInstance()

// Extract the current month from the calendar (Months are indexed from 0, so January = 0, February = 1, etc.)
            val currentMonth = calendar.get(Calendar.MONTH)

// Extract the current year from the calendar
            val currentYear = calendar.get(Calendar.YEAR)

// Filter the list to include only the orders that belong to the current month and year
            val filteredOrders = listOfData.data.filter { order ->

                // Create a new Calendar instance and set its time to the order's timestamp (milliseconds)
                val orderCalendar = Calendar.getInstance().apply { timeInMillis = order.date }

                // Extract the month of the order from the converted timestamp
                val orderMonth = orderCalendar.get(Calendar.MONTH)

                // Extract the year of the order from the converted timestamp
                val orderYear = orderCalendar.get(Calendar.YEAR)

                // Check if the order's month and year match the current month and year
                orderMonth == currentMonth && orderYear == currentYear
            }

// Calculate the total expense for the current month by summing up the (price * quantity) of each order
            val totalExpense = filteredOrders.sumOf { it.productFinalPrice.toInt() * it.noOfproducts.toInt() }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Expense Summary
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Total Expense: Rs $totalExpenseLifeTime",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Current Month: Rs $totalExpense",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // List of Expenses
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredOrders) { order ->
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
