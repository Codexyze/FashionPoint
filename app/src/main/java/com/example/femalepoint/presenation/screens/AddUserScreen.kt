package com.example.female

import com.example.femalepoint.presenation.screens.ErrorScreen
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.femalepoint.LocalNotification.createChannel
import com.example.femalepoint.LocalNotification.pushPaymentSucessfulNotification
import com.example.femalepoint.R
import com.example.femalepoint.data.OrderDetails
import com.example.femalepoint.data.UsersDetails
import com.example.femalepoint.navigation.HOMESCREEN
import com.example.femalepoint.navigation.ORDERSCREEN
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun AddUserDataScreen(navController: NavController,viewModel: MyViewModel= hiltViewModel(), productname:String
                      ,productcategory:String,productimage:String,productinitailprice:String,
                      productfinalprice:String,productID:String,noOfUnitsQuntity:Int
                      ) {
    val context= LocalContext.current
    val permissionState= remember { mutableStateOf(false) }
    val getDataForStoring=viewModel.getUserDataForStoring.collectAsState()
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

    val data = getDataForStoring.value.data
    val persmissionlauncher= rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(), onResult = {
            if(it){
                //permission granted
                permissionState.value=true

            }else{
                FancyToast.makeText(context,"SUCESSFUL",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show()
            }
        })

     permissionState.value = ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    LaunchedEffect(Unit) {
        viewModel.getUserDetailsForOrder()
        if (permissionState.value){
            createChannel(context = context)

        }else{

            persmissionlauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

        }



    }
    Log.d("PRODUCTID",productID)
    Log.d("DATAFROM",data.toString())

    val adduserdatastate=viewModel.addUserState.collectAsState()
    val orderstate=viewModel.orderproductstatestate.collectAsState()
    val updateStockState=viewModel.updateStockState.collectAsState()


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

              ErrorScreen()
        }
    }

    else{

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            LazyColumn {
              item {
                  Spacer(modifier = Modifier.height(16.dp))
                  Text("Shopping details", fontSize = 35.sp)

                  Image(
                      painter = painterResource(R.drawable.shopping),
                      contentDescription = "shopping", modifier = Modifier
                          .fillMaxWidth(0.85f)
                          .height(350.dp)
                  )
                  if (data?.name?.isNotEmpty() ==true){
                      name.value=data?.name.toString()
                  }else{
                      name.value=""
                  }
                  OutlinedTextField(value = name.value, onValueChange = {
                      name.value=it
                  }, placeholder = {
                      Text(if(data?.name?.isNotEmpty() ==true){"Name: ${data.name.toString()}"} else "Name")
                  })
                  Spacer(modifier = Modifier.height(16.dp))
                  if (data?.email?.isNotEmpty() ==true){
                      email.value=data.email.toString()
                  }else{
                      email.value=""
                  }
                  OutlinedTextField(value = email.value, onValueChange = {
                      email.value=it
                  }, placeholder = {
                     Text( if(data?.email?.isNotEmpty() ==true){"Email: ${data.email.toString()}"} else "Email")
                  })
                  Spacer(modifier = Modifier.height(16.dp))
                    if (data?.age?.isNotEmpty() ==true){
                        age.value=data?.age.toString()
                    }else{
                        age.value=""
                    }
                  OutlinedTextField(value = age.value, onValueChange = {
                      age.value=it
                  }, placeholder = {
                     // Text("Age")
                      Text( if(data?.age?.isNotEmpty() ==true){"age: ${data.age.toString()}"} else "Age")
                  })

                  Spacer(modifier = Modifier.height(16.dp))
                  if (data?.address?.isNotEmpty() ==true){
                      adress.value=data?.address.toString()
                  }else{
                      adress.value=""

                  }
                  OutlinedTextField(value = adress.value, onValueChange = { adress.value=it },
                      placeholder = {
                          //Text("Adress")
                          Text( if(data?.address?.isNotEmpty() ==true){"Address: ${data.address.toString()}"} else "Address")
                      })

                  Spacer(modifier = Modifier.height(16.dp))

                  if (data?.phonenumber?.isNotEmpty() ==true){
                      phonenumber.value=data.phonenumber.toString()
                  }else{
                      phonenumber.value=""

                  }
                  OutlinedTextField(value = phonenumber.value, onValueChange = { phonenumber.value=it },
                      placeholder = {
                         // Text("Phonenumber")
                          Text( if(data?.phonenumber?.isNotEmpty() ==true){"Phonenumber: ${data.phonenumber.toString()}"} else "Phonenumber")

                      })

                      if (data?.phonenumber2?.isNotEmpty() ==true){
                          phonenumber2.value=data.phonenumber2.toString()
                      }else{
                          phonenumber2.value=""
                      }


                  Spacer(modifier = Modifier.height(16.dp))
                  OutlinedTextField(value = phonenumber2.value, onValueChange = { phonenumber2.value=it },
                      placeholder = {
                          //Text("Phonenumber2")
                          Text( if(data?.phonenumber2?.isNotEmpty() ==true){"Phonenumber2: ${data.phonenumber2.toString()}"} else "Phonenumber2")

                      })
                  Spacer(modifier = Modifier.height(16.dp))
                  if (data?.pincode?.isNotEmpty() ==true){
                      pincode.value=data.pincode.toString()
                  }else{
                      pincode.value=""
                  }
                  OutlinedTextField(value = pincode.value, onValueChange = { pincode.value=it },
                      placeholder = {
                          //Text("Pincode")
                          Text( if(data?.pincode?.isNotEmpty() ==true){"Pincode: ${data.pincode.toString()}"} else "Pincode")

                      })
                  Spacer(modifier = Modifier.height(16.dp))
                  if (data?.state?.isNotEmpty() ==true){
                      state.value=data.state.toString()
                  }else{
                      state.value=""
                  }
                  OutlinedTextField(value = state.value, onValueChange = { state.value=it },
                      placeholder = {
                        //  Text("State")
                          Text( if(data?.state?.isNotEmpty() ==true){"State: ${data.state.toString()}"} else "State")
                      })
                  Spacer(modifier = Modifier.height(16.dp))
                  if (data?.nearbyPoints?.isNotEmpty() ==true){
                      nearbyPoints.value=data.nearbyPoints.toString()
                  }else{
                      nearbyPoints.value=""
                  }
                  OutlinedTextField(value = nearbyPoints.value, onValueChange = {
                      nearbyPoints.value=it },
                      placeholder = {
                      //    Text("NearbyPoints")
                          Text( if(data?.nearbyPoints?.isNotEmpty() ==true){"NearbyPoints: ${data.nearbyPoints.toString()}"} else "Landmark")

                      })
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
                                  )//deprecated feature
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
                                      noOfproducts = noOfUnits.value.toInt(),
                                      date=System.currentTimeMillis()

                                  )


                                  viewModel.addUserDetails(usersDetails = userdeatils)
                                  viewModel.placeOrder(orderDetails = order)
                                  Log.d("PRODUCTID",productID)
                                  if (noOfUnitsQuntity> noOfUnits.value.toInt()){
                                      val value=noOfUnitsQuntity-noOfUnits.value.toInt()
                                     viewModel.updateStockByIdAndStock(productID = productID,
                                         stock = value)
                                      pushPaymentSucessfulNotification(context = context)
                                      navController.navigate(HOMESCREEN){
                                          navController.popBackStack(ORDERSCREEN,true)
                                      }

                                      FancyToast.makeText(context,"SUCESSFUL",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show()

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
                      Spacer(modifier = Modifier.height(200.dp))


                  }
              }

            }
        }

    }

}

