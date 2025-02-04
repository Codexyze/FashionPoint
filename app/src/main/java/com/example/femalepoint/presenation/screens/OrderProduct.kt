package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.femalepoint.FiraSansFamily
import com.example.femalepoint.data.Product
import com.example.femalepoint.navigation.ALLREVIVEWSCREEN
import com.example.femalepoint.navigation.POSTORDERSCREEN
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun OrderProductScreen(
    navController: NavController,produt_id:String,imageUri:String,price:Int,finalprice:Int,name:String,
             discription:String,category:String,noOfUnits:Int,viewModel: MyViewModel= hiltViewModel()
) {
    val addToCartState = viewModel.addToCart.collectAsState()
    val context = LocalContext.current
    if (addToCartState.value.isloading) {
        LoadingBar()
    } else if (addToCartState.value.error.isNotEmpty()) {
        FancyToast.makeText(
            context,
            "Error From Server",
            FancyToast.LENGTH_LONG,
            FancyToast.ERROR,
            false
        ).show()
    } else {

        LazyColumn {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Product Name
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontFamily = FiraSansFamily
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Product Image
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Product Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Product Description
                    Text(
                        text = discription,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        fontFamily = FiraSansFamily,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Original Price with strike-through
                    Text(
                        text = "Price: $price",
                        style = TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 28.sp,
                            color = Color.Red,
                            fontFamily = FiraSansFamily
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Final Price highlighted
                    Text(
                        text = "Final Price: $finalprice",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 35.sp,
                            color = Color.Green,
                            fontFamily = FiraSansFamily
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            navController.navigate(
                                POSTORDERSCREEN(
                                    produt_id = produt_id,
                                    productImage = imageUri,
                                    productname = name,
                                    initialprice = price.toString(),
                                    finalprice = finalprice.toString(),
                                    productCategory = category,
                                    noOfUnits = noOfUnits
                                )
                            ) {
                                navController.popBackStack()
                            }
                            // Toast.makeText(context, "Processing", Toast.LENGTH_SHORT).show()
                            FancyToast.makeText(
                                context,
                                "Processing...",
                                FancyToast.LENGTH_LONG,
                                FancyToast.WARNING,
                                false
                            ).show()
                            // activity.startPayment()///todo todo
                        },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFFFAFB8), // Button background color
                            contentColor = Color.Black         // Text color
                        )
                    ) {
                        Text(
                            text = "Order",
                            fontFamily = FiraSansFamily
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            //todo here
                            val product = Product(
                                name = name,
                                price = price,
                                finalprice = finalprice,
                                imageUri = imageUri,
                                category = category,
                                description = discription,
                                noOfUnits = noOfUnits,
                                productid = produt_id
                            )
                            viewModel.addToCart(product = product)
                            FancyToast.makeText(
                                context,
                                "Added To Cart",
                                FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS,
                                false
                            ).show()

                        },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFFFAFB8), // Button background color
                            contentColor = Color.Black         // Text color
                        )
                    ) {
                        Text(
                            text = "Add to Cart",
                            fontFamily = FiraSansFamily
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {


                            navController.navigate(ALLREVIVEWSCREEN(productId = produt_id))

                        },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFFFAFB8), // Button background color
                            contentColor = Color.Black         // Text color
                        )
                    ) {
                        Text(
                            text = "Check Reviews",
                            fontFamily = FiraSansFamily
                        )


                    }
                    Spacer(modifier = Modifier.height(180.dp))
                }
            }
        }
    }
}