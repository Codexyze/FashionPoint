package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.femalepoint.data.Product


@Composable
fun SearchScreen(listOfProduct: List<Product>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(listOfProduct) { product ->
            ProductItem(price = product.price.toString(),name = product.name,finalPrice = product.finalprice.toString(),
                imageUri = product.imageUri,category = product.category,date = product.date.toString(),description = product.description,productID = product.productid)
            Spacer(modifier = Modifier.height(16.dp)) // Add spacing between items

        }
    }
}

@Composable
fun ProductItem(price:String="",name:String="",finalPrice:String="",
                imageUri:String="",category:String="",date:String="",description:String="",productID:String="") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically // Center items vertically
    ) {
        // Product Image
        AsyncImage(
            model = imageUri,
            contentDescription = "Product Image",
            modifier = Modifier
                .size(200.dp),
            contentScale = ContentScale.Fit
        )

        // Name and Category
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {

            // Product Name
            Text(
                text = name,

                maxLines = 1,
                overflow = TextOverflow.Ellipsis // Add "..." if text overflows
            )

            // Product Category
            Text(
                text =category,

                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}