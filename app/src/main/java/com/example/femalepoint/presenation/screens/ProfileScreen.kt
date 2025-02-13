package com.example.femalepoint.presenation.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.femalepoint.data.Userdata
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun ProfileScreen(viewModel: MyViewModel= hiltViewModel(),navController: NavController) {
  val userDataStoreforOrderState=viewModel.userDetailsForOrderState.collectAsState()

    //Todo Update Profile Screen
    val profilePicupdateState=viewModel.profilePicUpdateState.collectAsState()
    val name = remember { mutableStateOf("") }
    val id = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phonenumber = remember { mutableStateOf("") }
    val phonenumber2 = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    // val image = remember { mutableStateOf("") }
    val pincode = remember { mutableStateOf("") }
    val state = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val nearbyPoints = remember { mutableStateOf("") }
    val context= LocalContext.current
    LaunchedEffect (Unit){
        id.value=FirebaseAuth.getInstance().currentUser?.uid.toString()
        viewModel.getProfilePictureAfterUpdate()


    }
    //name, email,phonenumber,phonenumber2,address,image,pincode,state,age,nearbyPoints
    if (userDataStoreforOrderState.value.isloading&&
        profilePicupdateState.value.isloading
        ){
        LoadingIndicator()
    }
    else if(userDataStoreforOrderState.value.error.isNotEmpty()&&
        profilePicupdateState.value.error.isNotEmpty() ){
          ErrorScreen()

    }
    else {
        val getProfilePictureAfterUpdateState=viewModel.getProfileAfterUpdate.collectAsState()
        Log.d("PROFILEPICTURE",getProfilePictureAfterUpdateState.value.data.toString())
        val imageurl= remember { mutableStateOf("") }
        val media= rememberLauncherForActivityResult(
            contract =  ActivityResultContracts.PickVisualMedia(),
            onResult = {
                imageurl.value= it.toString()
                viewModel.updateProfileImage(it.toString())
                //uploade
            }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Profile", fontSize = 35.sp)
                        Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .clickable {
                             media.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))


                            }, // Handle image picker on click
                        contentAlignment = Alignment.Center
                    ) {

                        if (imageurl.value.isNotEmpty()) {
                            AsyncImage(
                                model = imageurl.value,
                                contentDescription = "Profile Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else if (!getProfilePictureAfterUpdateState.value.data?.imageUri.isNullOrEmpty()) {
                            AsyncImage(
                                model = getProfilePictureAfterUpdateState.value.data?.imageUri,
                                contentDescription = "Profile Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Default Profile Icon",
                                tint = Color.White,
                                modifier = Modifier.size(60.dp)
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(16.dp))


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
                                    val userData = Userdata(
                                        id = id.value,
                                        name = name.value,
                                        email = email.value,
                                        phonenumber = phonenumber.value,
                                        phonenumber2 = phonenumber2.value,
                                        address = address.value,
                                        pincode = pincode.value,
                                        state = state.value,
                                        age = age.value,
                                        nearbyPoints = nearbyPoints.value
                                    )
                                    viewModel.addUserDetailsForOrder(userData = userData)
                                    FancyToast.makeText(context, "Details Updated Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show()

                                    // viewModel.addUserDetailsForOrder()
                                } catch (e: Exception) {
                                    FancyToast.makeText(context, "Details Failed to be Uploaded", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()

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
                        Spacer(modifier = Modifier.height(200.dp))

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
