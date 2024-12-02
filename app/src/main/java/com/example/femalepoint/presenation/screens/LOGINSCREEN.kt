package com.example.femalepoint.presenation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.femalepoint.navigation.HOMESCREEN
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun LoginScreen(
    viewModel: MyViewModel = hiltViewModel(), // Inject the ViewModel
    navController: NavController // Navigation controller for navigating between screens
) {
    // Context for showing Toast messages
    val context = LocalContext.current

    // Collecting login state from the ViewModel as a state
    val loginState = viewModel.loginstate.collectAsState()

    // Email and Password states for the input fields
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // Display a loading indicator if login is in progress
    if (loginState.value.isloading) {
        LoadingBar()
    } else {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(16.dp), // Add padding for better UI appearance
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title text
            Text(
                text = "Login here...",
                style = MaterialTheme.typography.titleLarge, // Use a predefined style for consistency
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Email input field
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(), // Make input fields stretch across the screen width
                singleLine = true // Prevent multiline input
            )

            Spacer(modifier = Modifier.height(16.dp)) // Space between input fields

            // Password input field
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation() // Mask password input
            )

            Spacer(modifier = Modifier.height(24.dp)) // Space before button

            // Signup button
            OutlinedButton(
                onClick = {
                    when {
                        email.value.isEmpty() -> {
                            // Show a Toast if email is empty
                            FancyToast.makeText(
                                context,
                                "Fill email",
                                FancyToast.LENGTH_LONG,
                                FancyToast.WARNING,
                                false
                            ).show()
                        }
                        password.value.isEmpty() -> {
                            // Show a Toast if password is empty
                            FancyToast.makeText(
                                context,
                                "Fill password",
                                FancyToast.LENGTH_LONG,
                                FancyToast.WARNING,
                                false
                            ).show()
                        }
                        else -> {
                            // Attempt login via ViewModel
                            try {
                                viewModel.loginusingEmailPassword(
                                    email = email.value,
                                    password = password.value
                                )
                                if (loginState.value.data != null) {
                                    // Navigate to the home screen on successful login
                                    navController.navigate(HOMESCREEN)
                                }
                            } catch (e: Exception) {
                                // Show an error Toast on exception
                                Toast.makeText(context, "Failed to authenticate.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth() // Make the button stretch across the screen width
            ) {
                Text("Login")
            }

            // Show an error message if any error occurs
            if (loginState.value.error.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Oops! Something went wrong: ${loginState.value.error}",
                    color = MaterialTheme.colorScheme.error, // Use an error color
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
