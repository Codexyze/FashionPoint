package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.femalepoint.presenation.navigation.HOMESCREEN
import com.example.femalepoint.presenation.navigation.LOGINSCREEN
import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.shashank.sony.fancytoastlib.FancyToast


@Composable
fun SingUpScreen(
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {
    val createUserState = viewModel.createuserstate.collectAsState()
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFAFB8))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create an Account",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD81B60)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (createUserState.value.error.isNotEmpty()) {
                    Text(
                        text = "⚠️ ${createUserState.value.error}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        when {
                            email.value.isEmpty() -> {
                                FancyToast.makeText(
                                    context, "Enter email", FancyToast.LENGTH_LONG,
                                    FancyToast.WARNING, false
                                ).show()
                            }
                            password.value.isEmpty() -> {
                                FancyToast.makeText(
                                    context, "Enter password", FancyToast.LENGTH_LONG,
                                    FancyToast.WARNING, false
                                ).show()
                            }
                            else -> {
                                try {
                                    viewModel.emailPasswordAuthentication(email.value, password.value)
                                    if (createUserState.value.data != null) {
                                        navController.navigate(HOMESCREEN) {
                                            popUpTo(HOMESCREEN) { inclusive = true }
                                        }
                                    }
                                } catch (e: Exception) {
                                    FancyToast.makeText(
                                        context, "Failed", FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR, false
                                    ).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD81B60), contentColor = Color.White)
                ) {
                    Text(text = "Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = { navController.navigate(LOGINSCREEN) }) {
                    Text(
                        text = "Already have an account? Login",
                        color = Color(0xFFD81B60)
                    )
                }
            }
        }
    }
}

