package com.example.femalepoint.presenation.screens

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@Composable
fun ReelsVideoScreen(viewModel: MyViewModel= hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getAllVideosFromServer()

    }
    val reels=viewModel.getAllReelVideosFromStorage.collectAsState()
    Log.d("REELS","${reels.value.data.toString()}")
    Text("${reels.value.data.toString()}")

    //get all reels
}