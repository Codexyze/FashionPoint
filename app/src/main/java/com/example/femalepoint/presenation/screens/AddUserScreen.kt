package com.example.femalepoint.presenation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.femalepoint.R
import com.example.femalepoint.data.OrderDetails
import com.example.femalepoint.data.UsersDetails
import com.example.femalepoint.navigation.ORDERSCREEN
import com.example.femalepoint.navigation.PAYMENTSCREEN
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun AddUserDataScreen(navController: NavController,viewModel: MyViewModel= hiltViewModel(), productname:String
                      ,productcategory:String,productimage:String,productinitailprice:String,
                      productfinalprice:String,productID:String,noOfUnitsQuntity:Int
                      ) {
    Log.d("PRODUCTID",productID)

    val adduserdatastate=viewModel.addUserState.collectAsState()
    val orderstate=viewModel.orderproductstatestate.collectAsState()
    val updateStockState=viewModel.updateStockState.collectAsState()
    val context= LocalContext.current
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val adress = remember { mutableStateOf("") }
    val phonenumber= remember { mutableStateOf("") }
    val phonenumber2= remember { mutableStateOf("") }
    val pincode= remember { mutableStateOf("") }
    val state= remember { mutableStateOf("") }
    val nearbyPoints= remember { mutableStateOf("") }
    val noOfUnits= remember { mutableStateOf("") }
    if (adduserdatastate.value.isloading||orderstate.value.isloading||updateStockState.value.isloading){
 Column(modifier = Modifier.fillMaxSize(),
     horizontalAlignment = Alignment.CenterHorizontally,
     verticalArrangement = Arrangement.Center) {
     CircularProgressIndicator()

 }
    }
    else if(adduserdatastate.value.error.isNotEmpty()||orderstate.value.error.isNotEmpty()||updateStockState.value.error.isNotEmpty()){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Text("ERROR FROM SERVER SIDE ")
        }
    }

    else{
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            LazyColumn {
              item {

                  Image(
                      painter = painterResource(R.drawable.shopping),
                      contentDescription = "shopping", modifier = Modifier
                          .fillMaxWidth(0.8f)
                          .height(350.dp)
                  )
                  OutlinedTextField(value = name.value, onValueChange = {
                      name.value=it
                  }, placeholder = {
                      Text("Name")
                  })
                  Spacer(modifier = Modifier.height(16.dp))

                  OutlinedTextField(value = email.value, onValueChange = {
                      email.value=it
                  }, placeholder = {
                      Text("Email")
                  })
                  Spacer(modifier = Modifier.height(16.dp))

                  OutlinedTextField(value = age.value, onValueChange = {
                      age.value=it
                  }, placeholder = {
                      Text("Age")
                  })

                  Spacer(modifier = Modifier.height(16.dp))
                  OutlinedTextField(value = adress.value, onValueChange = { adress.value=it },
                      placeholder = { Text("Adress") })

                  Spacer(modifier = Modifier.height(16.dp))
                  OutlinedTextField(value = phonenumber.value, onValueChange = { phonenumber.value=it },
                      placeholder = { Text("Phonenumber") })

                  Spacer(modifier = Modifier.height(16.dp))
                  OutlinedTextField(value = phonenumber2.value, onValueChange = { phonenumber2.value=it },
                      placeholder = { Text("Phonenumber2") })
                  Spacer(modifier = Modifier.height(16.dp))
                  OutlinedTextField(value = pincode.value, onValueChange = { pincode.value=it },
                      placeholder = { Text("Pincode") })
                  Spacer(modifier = Modifier.height(16.dp))
                  OutlinedTextField(value = state.value, onValueChange = { state.value=it },
                      placeholder = { Text("State") })
                  Spacer(modifier = Modifier.height(16.dp))
                  OutlinedTextField(value = nearbyPoints.value, onValueChange = { nearbyPoints.value=it },
                      placeholder = { Text("NearbyPoints") })
                  Spacer(modifier = Modifier.height(16.dp))
                 OutlinedTextField(value = noOfUnits.value, onValueChange = {
                     noOfUnits.value=it
                 }, placeholder = {
                     Text("Quantity")
                 })
                  Spacer(modifier = Modifier.height(16.dp))

                  Row(horizontalArrangement = Arrangement.Center) {
                      OutlinedButton(
                          onClick = {
                              try {
                                  val userdeatils=UsersDetails(
                                      name = name.value,
                                      email = email.value,
                                      age = age.value
                                  )
                                  val order=OrderDetails(
                                     productID = productID,
                                      address = adress.value,
                                      phonenumber = phonenumber.value,
                                      phonenumber2 = phonenumber2.value,
                                      pincode = pincode.value,
                                      state = state.value,
                                      nearbyPoints = nearbyPoints.value,
                                      productname = productname,
                                      productCategory = productcategory,
                                      productImage = productimage,
                                      productinitialprice = productinitailprice,
                                    productFinalPrice = productfinalprice,
                                      noOfproducts = noOfUnits.value.toInt()

                                  )


                                  viewModel.addUserDetails(usersDetails = userdeatils)
                                  viewModel.placeOrder(orderDetails = order)
                                  Log.d("PRODUCTID",productID)
                                  if (noOfUnitsQuntity> noOfUnits.value.toInt()){
                                      val value=noOfUnitsQuntity-noOfUnits.value.toInt()
                                     viewModel.updateStockByIdAndStock(productID = productID,
                                         stock = value)
                                      FancyToast.makeText(context,"SUCESSFUL",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show()
                                      navController.navigate(PAYMENTSCREEN){
                                          navController.popBackStack(ORDERSCREEN,true)
                                      }
                                  }
                                 else{
                                      FancyToast.makeText(context,"Stock Out !!!",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show()
                                  }

                              } catch (e: Exception) {
                                  FancyToast.makeText(context,"Error from server",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true);
                              }
                          },
                          modifier = Modifier.fillMaxWidth(0.8f),
                          colors = ButtonDefaults.outlinedButtonColors(
                              containerColor = Color(0xFFFFAFB8), // Button background color
                              contentColor = Color.Black         // Text color
                          )
                      ) {
                          Text(text = "ADD USER DETAILS")
                      }


                  }
              }

            }
        }

    }

}
