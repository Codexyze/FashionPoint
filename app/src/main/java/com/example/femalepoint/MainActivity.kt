package com.example.femalepoint

import android.app.Activity
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
import com.example.femalepoint.navigation.HomeScreen


import com.example.femalepoint.ui.theme.FemalePointTheme
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener

import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

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

                  }
                }
                }
            }

        }
    fun startPayment() {
        //payment gateway
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","http://example.com/image/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","50000")//pass amount in currency subunits

            val retryObj =  JSONObject()
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        TODO("Not yet implemented")

    }

}


