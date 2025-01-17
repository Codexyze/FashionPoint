package com.example.femalepoint.presenation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
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
import com.example.femalepoint.navigation.SEARCHSCREEN
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.shashank.sony.fancytoastlib.FancyToast
//
//
//@Composable
//fun SearchScreen(myViewModel: MyViewModel= hiltViewModel(),navController: NavController) {
//    val search= remember { mutableStateOf("") }
//    val searchstate = myViewModel.searchProductState.collectAsState()
//    Row(modifier = Modifier.fillMaxWidth()) {
//        OutlinedTextField(
//            value = search.value,
//            onValueChange = { search.value = it },
//            label = { Text("Search") },
//            modifier = Modifier
//                .fillMaxWidth(0.85f)
//                .padding(4.dp)
//        )
//        IconButton(onClick = {
//            //todo
//            myViewModel.searchProduct(search.value)
//Log.d("DATASEARCH",searchstate.value.data.toString())
//
//
//        }) {
//            Icon(
//                imageVector = Icons.Default.Search,
//                contentDescription = "Search",
//            )
//        }
//    }
//    if(searchstate.value.data.isNullOrEmpty()) {
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(8.dp)
//        ) {
//            items(searchstate.value.data) { product ->
//                ProductItem(
//                    price = product.price.toString(),
//                    name = product.name,
//                    finalPrice = product.finalprice.toString(),
//                    imageUri = product.imageUri,
//                    category = product.category,
//                    date = product.date.toString(),
//                    description = product.description,
//                    productID = product.productid
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }
//    }else{
//        Text("No product")
//    }
//    }
//
//
//
//
//@Composable
//fun ProductItem(price:String="",name:String="",finalPrice:String="",
//                imageUri:String="",category:String="",date:String="",description:String="",productID:String="") {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically // Center items vertically
//    ) {
//        // Product Image
//        AsyncImage(
//            model = imageUri,
//            contentDescription = "Product Image",
//            modifier = Modifier
//                .size(200.dp),
//            contentScale = ContentScale.Fit
//        )
//
//        // Name and Category
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            verticalArrangement = Arrangement.Center
//        ) {
//
//            // Product Name
//            Text(
//                text = name,
//
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis // Add "..." if text overflows
//            )
//
//            // Product Category
//            Text(
//                text =category,
//
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//        }
//    }
//}
@Composable
fun SearchScreen(myViewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    // State to hold search input
    val search = remember { mutableStateOf("") }

    // Observing search state from the ViewModel
    val searchState = myViewModel.searchProductState.collectAsState()

    // Search Input Row
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        // Search Input Field
        OutlinedTextField(
            value = search.value,
            onValueChange = { search.value = it },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(end = 8.dp)
        )
        // Search Button
        IconButton(
            onClick = {
                if (search.value.isNotBlank()) {
                    myViewModel.searchProduct(search.value) // Trigger search
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
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    }

    // Display based on state
    when {
        searchState.value.isloading -> {
            // Show Loading State
            Text("Loading...")
        }

        searchState.value.data.isNullOrEmpty() -> {
            // Show Results in LazyColumn
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
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
                        productID = product.productid
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        else -> {
            // Show No Results Message
            Text(
                text = "No products found",
                color = androidx.compose.ui.graphics.Color.Gray
            )
        }
    }
}

@Composable
fun ProductItem(
    price: String = "",
    name: String = "",
    finalPrice: String = "",
    imageUri: String = "",
    category: String = "",
    date: String = "",
    description: String = "",
    productID: String = ""
) {
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
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        // Product Details
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Category: $category",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Price: $price | Final: $finalPrice",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
