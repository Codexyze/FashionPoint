package com.example.femalepoint.presenation.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import com.example.femalepoint.presenation.navigation.ORDERSCREEN
import com.example.femalepoint.presenation.commonutils.LoadingBar
import com.example.femalepoint.presenation.viewmodel.MyViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReelsVideoScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.getReelsMappedWithProductID()
    }

    val reelsState = viewModel.getReelsMappedWithProductIDState.collectAsState()
    Log.d("LISTOFREELS", "${reelsState.value.data}")

    when {
        reelsState.value.isloading -> LoadingBar()
        reelsState.value.error.isNotEmpty() -> ErrorScreen()
        else -> {
            val videoList = reelsState.value.data ?: emptyList()
            val pagerState = rememberPagerState(pageCount = { videoList.size })

            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val videoUrl = videoList[page].videoUrl
                val productID = videoList[page].productID

                // Pass dynamically updated `isPlaying` state
                VideoPlayer(
                    videoUrl = videoUrl,
                    isPlaying = page == pagerState.currentPage, // Only play the current page
                    productID = productID,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun VideoPlayer(videoUrl: String, isPlaying: Boolean, productID: String
                ,navController: NavHostController,viewModel: MyViewModel= hiltViewModel()
) {
    val getProductByIdState=viewModel.getProductByIDStateState.collectAsState()
    val context = LocalContext.current
    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    LaunchedEffect(isPlaying) {
        Log.d("VIDEO_PLAYER", "Playing: $isPlaying for $videoUrl")
        exoPlayer.playWhenReady = isPlaying
    }

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                //ORDERSCREEN
                viewModel.getProductByID(productID = productID)
               if(getProductByIdState.value.data!=null) {
                   //produt_id:String,imageUri:String,price:Int,finalprice:Int,name:String,
                   //             discription:String,category:String,noOfUnits:Int
                   navController.navigate(
                       ORDERSCREEN(
                           produt_id = getProductByIdState.value.data!!.productid,
                           imageUri = getProductByIdState.value.data!!.imageUri,
                           price = getProductByIdState.value.data!!.price,
                           finalprice = getProductByIdState.value.data!!.finalprice,
                           name = getProductByIdState.value.data!!.name,
                           discription = getProductByIdState.value.data!!.description,
                           category = getProductByIdState.value.data!!.category,
                           noOfUnits = getProductByIdState.value.data!!.noOfUnits
                       )
                   )
               }

            },
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
            }
        }
    )
}
