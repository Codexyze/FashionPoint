package com.example.femalepoint.presenation.screens
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.femalepoint.constants.Constants
import com.example.femalepoint.navigation.CARTSCREEN
import com.example.femalepoint.navigation.REELSVIDEOSCREEN
import com.shashank.sony.fancytoastlib.FancyToast

@Composable
fun SeetingsScreen(navController: NavController) {
    val permissionState= remember { mutableStateOf(false) }
    val context= LocalContext.current
    val permissionLauncher= rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
        onResult = {
            if(it){
               permissionState.value=true
            }
        }
    )
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {


            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp).clickable {
                            navController.navigate(CARTSCREEN)
                        }, // Add padding around the card
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(12.dp) // Rounded corners for a better look
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), // Add internal padding for better spacing
                        verticalAlignment = Alignment.CenterVertically // Align items in the center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            modifier = Modifier.size(40.dp), // Increased icon size
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp)) // Increased spacing for better layout
                        Text(
                            text = "Cart",
                            fontSize = 22.sp, // Slightly larger text
                            fontWeight = FontWeight.Bold
                        )

                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp).clickable {
                            if(permissionState.value){
                                try {
                                    val callIntent = Intent(Intent.ACTION_CALL).apply {
                                        data = Uri.parse("tel:${Constants.PHONENUMBER}")
                                    }
                                    context.startActivity(callIntent)
                                }catch (e :Exception){
                                    FancyToast.makeText(context,"Error",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show()
                                }
                            }else{
                                permissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
                            }
                        }, // Add padding around the card
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(12.dp) // Rounded corners for a better look
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), // Add internal padding for better spacing
                        verticalAlignment = Alignment.CenterVertically // Align items in the center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Cart",
                            modifier = Modifier.size(40.dp), // Increased icon size
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp)) // Increased spacing for better layout
                        Text(
                            text = "Contact us",
                            fontSize = 22.sp, // Slightly larger text
                            fontWeight = FontWeight.Bold
                        )

                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp).clickable {
                           navController.navigate(REELSVIDEOSCREEN)
                        }, // Add padding around the card
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(12.dp) // Rounded corners for a better look
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp), // Add internal padding for better spacing
                        verticalAlignment = Alignment.CenterVertically // Align items in the center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VideoLibrary,
                            contentDescription = "Reels",
                            modifier = Modifier.size(40.dp), // Increased icon size
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp)) // Increased spacing for better layout
                        Text(
                            text = "Reels",
                            fontSize = 22.sp, // Slightly larger text
                            fontWeight = FontWeight.Bold
                        )

                    }

                }



            }
        }
    }
}