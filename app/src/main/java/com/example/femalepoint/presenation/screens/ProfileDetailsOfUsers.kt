package com.example.femalepoint.presenation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable
fun ProfileDetailsOfUsers(viewmodel:MyViewModel= hiltViewModel(), userID:String) {
    LaunchedEffect(Unit) {
        viewmodel.getUserProfileDetailsDataByUserID(userID = userID)

        viewmodel.getProfilePictureAfterUpdate()
    }

    val getUserProfileDeailsDataState=viewmodel.getUserProfileDeailsDataState.collectAsState()
    val getProfilePictureAfterUpdateState=viewmodel.getProfileAfterUpdate.collectAsState()
    if(getUserProfileDeailsDataState.value.isloading){
        LoadingBar()
    }else if(getUserProfileDeailsDataState.value.error.isNotEmpty()){
        ErrorScreen()
    }else{

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Image with a shadow effect
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .shadow(8.dp, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = getProfilePictureAfterUpdateState.value.data?.imageUri,
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // User Information in a stylish card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text ="Name : ${getUserProfileDeailsDataState.value.data?.name} "?: "No Name",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "State : ${getUserProfileDeailsDataState.value.data?.state} "?: "No State",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

    }

}