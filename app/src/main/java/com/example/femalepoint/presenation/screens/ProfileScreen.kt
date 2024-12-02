package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.femalepoint.R
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable
fun ProfileScreen(viewModel: MyViewModel= hiltViewModel()) {
    val getLocationandDataState = viewModel.userDataState.collectAsState()
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phonenumber = remember { mutableStateOf("") }
    val phonenumber2 = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    // val image = remember { mutableStateOf("") }
    val pincode = remember { mutableStateOf("") }
    val state = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val nearbyPoints = remember { mutableStateOf("") }
    //name, email,phonenumber,phonenumber2,address,image,pincode,state,age,nearbyPoints
    if (getLocationandDataState.value.isloading) {
        LoadingIndicator()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Shopping details", fontSize = 35.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(R.drawable.shopping),
                        contentDescription = "shopping", modifier = Modifier.fillMaxWidth(0.8f)
                            .height(350.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = name.value, onValueChange = {
                        name.value = it
                    }, placeholder = {
                        Text(text = "Name")
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = email.value, onValueChange = {
                        email.value = it
                    }, placeholder = {
                        Text(text = "Email")
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = phonenumber.value, onValueChange = {
                        phonenumber.value = it
                    }, placeholder = {
                        Text(text = "Phone Number")
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = phonenumber2.value, onValueChange = {
                        phonenumber2.value = it
                    }, placeholder = {
                        Text(text = "Alternate Phone Number")
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = address.value, onValueChange = {
                        address.value = it
                    }, placeholder = {
                        Text(text = "Address")
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = pincode.value, onValueChange = {
                        pincode.value = it
                    }, placeholder = {
                        Text(text = "Pincode")
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(16.dp))//jjj
                    OutlinedTextField(value = state.value, onValueChange = {
                        state.value = it
                    }, placeholder = {
                        Text(text = "State")
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = age.value, onValueChange = {
                        age.value = it
                    }, placeholder = {
                        Text(text = "Age")
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = nearbyPoints.value, onValueChange = {
                        nearbyPoints.value = it
                    }, placeholder = {
                        Text(text = "Nearby Points")
                    }, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.Center) {
                        OutlinedButton(
                            onClick = {
                                try {
                                    viewModel.addUserLocationDetails(
                                        name = name.value, phonenumber = phonenumber.value,
                                        email = email.value, phonenumber2 = phonenumber2.value,
                                        pincode = pincode.value, address = address.value,
                                        state = state.value, age = age.value,
                                        nearbyPoints = nearbyPoints.value
                                    )
                                } catch (e: Exception) {

                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.8f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0xFFFFAFB8), // Button background color
                                contentColor = Color.Black         // Text color
                            )
                        ) {
                            Text(text = "Update")
                        }


                    }
                }

            }
        }
    }
}
@Composable
fun LoadingIndicator() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = Color.Magenta)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Please wait...", color = Color.Black)
    }
}
