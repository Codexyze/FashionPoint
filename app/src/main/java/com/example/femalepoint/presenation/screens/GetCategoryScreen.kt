package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.femalepoint.R
import com.example.femalepoint.navigation.ALLPRODUCTSCREEN
import com.example.femalepoint.navigation.MATCHEDSCREEN
import com.example.femalepoint.navigation.ORDERSCREEN
import com.example.femalepoint.presenation.commonutils.LoadingBar


import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable
fun GetcategoryScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    // Trigger the ViewModel to fetch data
    LaunchedEffect(Unit) {
        viewModel.getAllCategories()
        viewModel.getAllProducts()
        viewModel.getAllProductWithoutLimit()
    }

    // Collecting states
    val categoryState = viewModel.getAllCategoryState.collectAsState()
    val productState = viewModel.getproducts.collectAsState()
    val getallproductstate = viewModel.getAllProductsWithoutLimitState.collectAsState()
    val search= remember { mutableStateOf("") }
    if (categoryState.value.isloading || productState.value.isloading || getallproductstate.value.isloading) {
        // Show loading indicator
       LoadingBar()
    }
    else if (categoryState.value.error.isNotEmpty() || productState.value.error.isNotEmpty() || getallproductstate.value.error.isNotEmpty()) {
        Text("ERROR IN SERVER")
    }
    else {

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {


            // Categories LazyRow
            // AllProductScreen(navController = navController)
            LazyRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categoryState.value.data) { category ->
                    category?.let {
                        CategoryCard(categoryName = it.name, navController = navController)
                    }
                }
            }

            // Banner Image
            Image(
                painter = painterResource(R.drawable.banner),
                contentDescription = "Banner",
                modifier = Modifier.fillMaxWidth().clickable {
                    navController.navigate(ALLPRODUCTSCREEN)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Products LazyRow
            LazyRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(productState.value.data) {
                    CardProducts(
                        price = it!!.price,
                        imageUri = it.imageUri,
                        name = it.name,
                        finalPrice = it.finalprice,
                        description = it.description,
                        noOfUnits = it.noOfUnits,
                        navController = navController,
                        productId = it.productid,
                        category = it.category
                    )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // All Products Section


        }
    }

}
@Composable
fun CategoryCard(categoryName: String,navController: NavController,viewModel: MyViewModel= hiltViewModel()) {

    Card(
        modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .clickable {
                //todo

                navController.navigate(MATCHEDSCREEN(category = categoryName))

            }
        , // Card dimensions
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        // Center icon and text inside the card
       Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            // Circle for the icon
           Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = androidx.compose.ui.graphics.Color.LightGray,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Category Icon",
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }

            // Spacer for spacing
            Spacer(modifier = Modifier.height(8.dp))

            // Text for the category name
            Text(text = categoryName)
        }
    }
}

@Composable
fun CardProducts(price: Int, imageUri: String, name: String, finalPrice: Int, description: String
                 , noOfUnits: Int,navController: NavController,productId:String,
                 category:String) {
    LazyColumn {
        item {

            Card(
                modifier = Modifier
                    .height(300.dp) // Increased height for better layout
                    .width(200.dp)  // Adjusted width to accommodate content
                    .padding(8.dp), // Added padding around the card
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp) // Padding inside the card
                ) {
                    // Displaying the product image with proper scaling
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .height(180.dp)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    ORDERSCREEN(
                                        produt_id = productId,
                                        imageUri = imageUri,
                                        price = price,
                                        finalprice = finalPrice,
                                        name = name,
                                        discription = description,
                                        category = category,
                                        noOfUnits = noOfUnits
                                    )
                                )

                            },
                        contentScale = androidx.compose.ui.layout.ContentScale.Fit // Crop to fill the space

                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Space between image and text

                    // Displaying the product name
                    Text(
                        text = name,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1 // Prevent long text from overflowing
                    )

                    Spacer(modifier = Modifier.height(4.dp)) // Small space between text

                    // Displaying the product price
                    Text(
                        text = "Price: $price",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Displaying the product's final price
                    Text(
                        text = "Final Price: $finalPrice",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(onClick = {

                    }) {
                        Text("Order Now")
                    }


                }
            }
        }
    }
}



