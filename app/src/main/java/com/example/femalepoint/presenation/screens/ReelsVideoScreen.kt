package com.example.femalepoint.presenation.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReelsVideoScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.getAllVideosFromServer()
    }
//navcontroller for future migration
    val reels = viewModel.getAllReelVideosFromStorage.collectAsState()
    Log.d("REELS", "${reels.value.data.toString()}")

    if (reels.value.isloading) {
        LoadingBar()
    } else if (reels.value.error.isNotEmpty()) {
        Text("Error from server side")
    } else {
        val videoList = reels.value.data ?: emptyList()

        val pagerState = rememberPagerState(pageCount = { videoList.size }) // For vertical scrolling

        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val videoUrl = videoList[page].url
            VideoPlayer(videoUrl, isPlaying = pagerState.currentPage == page)
        }
    }
}

@Composable
fun VideoPlayer(videoUrl: String, isPlaying: Boolean) {
    val context = LocalContext.current

    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release() // Release when removed
        }
    }

    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying // Only play if it's the current item
    }

    AndroidView(
        modifier = Modifier.fillMaxSize().clickable {
            //go to product screen


        }, // Fullscreen video
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false // Hide controls like Instagram reels
            }
        }
    )
}
