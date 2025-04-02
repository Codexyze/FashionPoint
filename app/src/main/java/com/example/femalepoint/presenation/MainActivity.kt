package com.example.femalepoint.presenation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.femalepoint.R

import com.example.femalepoint.presenation.navigation.HomeScreen

import com.example.femalepoint.ui.theme.FemalePointTheme
import com.razorpay.PaymentResultListener

import dagger.hilt.android.AndroidEntryPoint

val FiraSansFamily = FontFamily(
    Font(R.font.poppinsmedium, FontWeight.Light),
    Font(R.font.poppinsregular, FontWeight.Normal)
)

@AndroidEntryPoint
class MainActivity : ComponentActivity(),PaymentResultListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FemalePointTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                  Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                      HomeScreen()
                     // ErrorScreen()
                  }
                }
                }
            }

        }

    override fun onPaymentSuccess(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()

    }

}


