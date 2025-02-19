package com.example.femalepoint.presenation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.femalepoint.navigation.HOMESCREEN
import com.example.femalepoint.navigation.SIGNUPCREEN
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast
//OlD UI
//@Composable
//fun LoginScreen(
//    viewModel: MyViewModel = hiltViewModel(), // Inject the ViewModel
//    navController: NavController // Navigation controller for navigating between screens
//) {
//    // Context for showing Toast messages
//    val context = LocalContext.current
//
//    // Collecting login state from the ViewModel as a state
//    val loginState = viewModel.loginstate.collectAsState()
//
//    // Email and Password states for the input fields
//    val email = remember { mutableStateOf("") }
//    val password = remember { mutableStateOf("") }
//
//    // Display a loading indicator if login is in progress
//    if (loginState.value.isloading) {
//        LoadingBar()
//    }else  if (loginState.value.error.isNotEmpty()) {
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(
//           // text = "Oops! Something went wrong: ${loginState.value.error}",
//            text = "WRONG PASSWORD OR EMAIL",
//            color = MaterialTheme.colorScheme.error, // Use an error color
//            style = MaterialTheme.typography.titleLarge
//        )
//    }
//
//
//    else {
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .verticalScroll(rememberScrollState())
//                .padding(16.dp), // Add padding for better UI appearance
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            // Title text
//            Text(
//                text = "Login here...",
//                style = MaterialTheme.typography.titleLarge, // Use a predefined style for consistency
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//
//            // Email input field
//            OutlinedTextField(
//                value = email.value,
//                onValueChange = { email.value = it },
//                label = { Text("Email") },
//                modifier = Modifier.fillMaxWidth(), // Make input fields stretch across the screen width
//                singleLine = true // Prevent multiline input
//            )
//
//            Spacer(modifier = Modifier.height(16.dp)) // Space between input fields
//
//            // Password input field
//            OutlinedTextField(
//                value = password.value,
//                onValueChange = { password.value = it },
//                label = { Text("Password") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                visualTransformation = PasswordVisualTransformation() // Mask password input
//            )
//
//            Spacer(modifier = Modifier.height(24.dp)) // Space before button
//
//            // Signup button
//            OutlinedButton(
//                onClick = {
//                    when {
//                        email.value.isEmpty() -> {
//                            // Show a Toast if email is empty
//                            FancyToast.makeText(
//                                context,
//                                "Fill email",
//                                FancyToast.LENGTH_LONG,
//                                FancyToast.WARNING,
//                                false
//                            ).show()
//                        }
//                        password.value.isEmpty() -> {
//                            // Show a Toast if password is empty
//                            FancyToast.makeText(
//                                context,
//                                "Fill password",
//                                FancyToast.LENGTH_LONG,
//                                FancyToast.WARNING,
//                                false
//                            ).show()
//                        }
//                        else -> {
//                            // Attempt login via ViewModel
//                            try {
//                                viewModel.loginusingEmailPassword(
//                                    email = email.value,
//                                    password = password.value
//                                )
//                                if (loginState.value.data != null) {
//                                    // Navigate to the home screen on successful login
//                                    if(FirebaseAuth.getInstance().currentUser!=null) {
//                                    navController.navigate(HOMESCREEN){
//                                        popUpTo(HOMESCREEN){
//                                            inclusive = true
//                                        }
//                                    }
//                                    }else {
//                                        FancyToast.makeText(
//                                            context,
//                                            "Try Reclicking",
//                                            FancyToast.LENGTH_LONG,
//                                            FancyToast.WARNING,
//                                            false
//                                        ).show()
//
//                                    }
//                                }
//                            } catch (e: Exception) {
//                                // Show an error Toast on exception
//                                Toast.makeText(context, "Failed to authenticate.", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth() // Make the button stretch across the screen width
//            ) {
//                Text("Login")
//            }
//
//            // Show an error message if any error occurs
//
//        }
//    }
//}

@Composable
fun LoginScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val loginState = viewModel.loginstate.collectAsState()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFAFB8)) // Soft pink background
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White) // Soft contrast card
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Welcome Back 👋",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD81B60) // Deep pink for title
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Error Message
                if (loginState.value.error.isNotEmpty()) {
                    Text(
                        text = "⚠️ Wrong Email or Password",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Email Input
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email Address") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Password Input
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                Button(
                    onClick = {
                        when {
                            email.value.isEmpty() -> {
                                FancyToast.makeText(
                                    context, "Please enter email", FancyToast.LENGTH_LONG,
                                    FancyToast.WARNING, false
                                ).show()
                            }
                            password.value.isEmpty() -> {
                                FancyToast.makeText(
                                    context, "Please enter password", FancyToast.LENGTH_LONG,
                                    FancyToast.WARNING, false
                                ).show()
                            }
                            else -> {
                                try {
                                    viewModel.loginusingEmailPassword(email.value, password.value)
                                    if (FirebaseAuth.getInstance().currentUser != null) {
                                        navController.navigate(HOMESCREEN) {
                                            popUpTo(HOMESCREEN) { inclusive = true }
                                        }
                                    } else {
                                        FancyToast.makeText(
                                            context, "Try Reclicking", FancyToast.LENGTH_LONG,
                                            FancyToast.WARNING, false
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD81B60), // Deep pink button
                        contentColor = Color.White // White text
                    )
                ) {
                    Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Signup Text
                TextButton(
                    onClick = { navController.navigate(SIGNUPCREEN) }
                ) {
                    Text(
                        text = "Don't have an account? Sign up",
                        color = Color(0xFFD81B60) // Deep pink text
                    )
                }
            }
        }

        // Loading Indicator
        if (loginState.value.isloading) {
            LoadingBar()
        }
    }
}
