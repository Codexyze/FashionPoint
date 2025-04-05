
package com.example.femalepoint.presenation.screens
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.femalepoint.data.model.Product
import com.example.femalepoint.presenation.navigation.ORDERSCREEN
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable

fun MatchingProducts(category: String, viewModel: MyViewModel = hiltViewModel(),navController: NavController) {
    // Call this function only when category changes
    LaunchedEffect(category) {
        viewModel.getMatchingProduct(category = category)
    }

    // Collect the matching product state
    val mydata = viewModel.matchingProductState.collectAsState().value.data

    // Log the size of the data list
    Log.d("MatchingProducts", "mydata size: ${mydata?.size ?: 0}")

    // Display the data in a LazyColumn
    if (mydata.isNullOrEmpty()) {
        // Show a placeholder when there's no data
        Text("No matching products available.")
    } else {
        LazyColumn {
            items(mydata) { product ->
                ForEachMatchingProduct(product, navController = navController)
            }
        }
    }
}


@Composable
fun ForEachMatchingProduct(data: Product,navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth().clickable {
                navController.navigate(ORDERSCREEN(produt_id = data.productid,
                    imageUri = data.imageUri,price = data.price,finalprice = data.finalprice,
                    name = data.name,discription = data.description,category = data.category
                    , noOfUnits = data.noOfUnits ))

            },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            // Product Image
            AsyncImage(
                model = data.imageUri,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(100.dp) // Fixed size for image
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .background(androidx.compose.ui.graphics.Color.LightGray)
                    .clickable {
                        // Handle click event

                    },
                contentScale = androidx.compose.ui.layout.ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Product Name
                Text(
                    text = data.name,
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = androidx.compose.ui.unit.TextUnit.Unspecified,
                        fontWeight =FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Prices
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₹${data.price}", // Initial price
                        style = androidx.compose.ui.text.TextStyle(
                            color = androidx.compose.ui.graphics.Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "₹${data.finalprice}", // Final price
                        style = androidx.compose.ui.text.TextStyle(
                            color = androidx.compose.ui.graphics.Color.Green,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Stock Units
                Text(
                    text = "Available Units: ${data.noOfUnits}",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = androidx.compose.ui.unit.TextUnit.Unspecified,
                        color = androidx.compose.ui.graphics.Color.Blue
                    )
                )
            }
        }
    }
}
