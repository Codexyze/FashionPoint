package com.example.femalepoint.presenation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.femalepoint.navigation.ORDERSCREEN
import com.example.femalepoint.navigation.SEARCHSCREEN
import com.example.femalepoint.presenation.viewmodel.MyViewModel


@Composable
fun AllProductScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    LaunchedEffect(Unit) {
        viewModel.getAllProductWithoutLimit()
    }
    val searchState=viewModel.searchProductState.collectAsState()
    val state = viewModel.getAllProductsWithoutLimitState.collectAsState()
    val context = LocalContext.current
    val search = remember { mutableStateOf("") }
    when {
        state.value.isloading && searchState.value.isloading-> {
            // Show loading indicator
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.value.error.isNotEmpty() && searchState.value.error.isNotEmpty()-> {
            // Show error message
            Toast.makeText(
                context,
                "Error loading products: ${state.value.error}",
                Toast.LENGTH_SHORT
            ).show()
        }



        state.value.data != null -> {
            // Display products in a grid
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text("Why not search products??" , modifier = Modifier.clickable {
                        navController.navigate(SEARCHSCREEN)

                    })
                }

                LazyVerticalGrid(
                    columns = Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {

                    items(state.value.data) { product ->
                        EachAllProductLook(
                            name = product.name,
                            price = product.price,
                            finalPrice = product.finalprice,
                            imageUri = product.imageUri,
                            category = product.category,
                            description = product.description,
                            noOfUnits = product.noOfUnits,
                            productId = product.productid,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EachAllProductLook(
    name: String,
    price: Int,
    finalPrice: Int,
    imageUri: String,
    category: String,
    description: String,
    noOfUnits: Int,
    productId: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
        ,
        elevation = cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize().clickable {

                    navController.navigate(ORDERSCREEN(
                        produt_id = productId,
                        imageUri = imageUri,
                        price = price,
                        finalprice = finalPrice,
                        name = name,
                        discription = description,
                        category = category,
                        noOfUnits = noOfUnits
                    ))
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Image
            AsyncImage(
                model = imageUri,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product Name
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Product Prices
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "₹$price",
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = "₹$finalPrice",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Green,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
