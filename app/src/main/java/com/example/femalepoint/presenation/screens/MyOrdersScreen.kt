package com.example.femalepoint.presenation.screens

import android.util.Log
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.femalepoint.navigation.ORDERHISTORYDETAILS
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable
fun MyOrderScreen(viewModel: MyViewModel = hiltViewModel(),navController: NavController) {
    // Fetching orders on composable launch
    LaunchedEffect(Unit) {
        viewModel.getAllOrders()
    }

    // Collecting state
    val listOfData = viewModel.getAllOrderState.collectAsState()

    if (listOfData.value.isloading) {
        LoadingBar()
    } else if (listOfData.value.error != "") {
        Text("CHECK INTERNET")
    } else {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Add spacing between items
            ) {
                Log.d("DATA", listOfData.value.data.toString())
                items(listOfData.value.data) { order ->
                    EachOrderItemLook(
                        productImage = order.productImage,
                        productCategory = order.productCategory,
                        productName = order.productname,
                        price = order.productinitialprice.toInt(),
                        finalprice = order.productFinalPrice.toInt(),
                        description = order.productname,
                        noOfUnits = order.noOfproducts,
                        productId = order.productID,
                        navController=navController

                    )
                }
            }
        }
    }
}


    @Composable
    fun EachOrderItemLook(
        productImage: String, productCategory: String, productName: String,
        price: Int, finalprice: Int, description: String, noOfUnits: Int, productId: String,
        navController: NavController
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp).clickable {
//                  navController.navigate(ORDERHISTORYDETAILS(
//                      productCategory = productCategory,productName=productName,
//                      price = price, finalprice = finalprice, noOfUnits = noOfUnits,
//                      description = description, productId = productId, imageUri = productImage
//                  ))


                }
            , // Add padding around each item
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            AsyncImage(
                model = productImage,
                contentDescription = "Product Image",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp), // Set fixed width for consistent layout
                contentScale =ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between image and text

            // Product Details
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = productName)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = productCategory,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                )
            }
        }
    }

