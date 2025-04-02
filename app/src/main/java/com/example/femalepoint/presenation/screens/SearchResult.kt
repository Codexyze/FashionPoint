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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.femalepoint.presenation.navigation.ORDERSCREEN
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun SearchScreen(myViewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    val search = remember { mutableStateOf("") }
    val searchState = myViewModel.searchProductState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        // Search Input Row
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = search.value,
                onValueChange = { search.value = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(end = 8.dp)
            )
            IconButton(
                onClick = {
                    if (search.value.isNotBlank()) {
                        myViewModel.searchProduct(search.value)
                    } else {
                        FancyToast.makeText(
                            navController.context,
                            "Please enter a search term",
                            FancyToast.LENGTH_SHORT,
                            FancyToast.WARNING,
                            true
                        ).show()
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }

        // Display State
        when {
            searchState.value.isloading -> {
                // Show a loading indicator
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            searchState.value.data.isNullOrEmpty() -> {
                // Show No Results Message
                Text(
                    text = "No products found",
                    modifier = Modifier.fillMaxSize().padding(top = 16.dp),
                    color = androidx.compose.ui.graphics.Color.Gray
                )
            }

            else -> {
                // Show Results in LazyColumn
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(searchState.value.data) { product ->
                        ProductItem(
                            price = product.price.toString(),
                            name = product.name,
                            finalPrice = product.finalprice.toString(),
                            imageUri = product.imageUri,
                            category = product.category,
                            date = product.date.toString(),
                            description = product.description,
                            productID = product.productid,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    price: String,
    name: String,
    finalPrice: String,
    imageUri: String,
    category: String,
    date: String,
    description: String,
    productID: String,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable {
                navController.navigate(ORDERSCREEN(produt_id = productID, imageUri = imageUri,
                    price = price.toInt(), finalprice = finalPrice.toInt(), name = name, discription = description,

                    category = category, noOfUnits = 1))

            }
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = "Product Image",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = name, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = "Category: $category", maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = "Price: $price | Final: $finalPrice", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}
