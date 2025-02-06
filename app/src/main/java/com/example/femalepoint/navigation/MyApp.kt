package com.example.femalepoint.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.femalepoint.presenation.screens.AddUserDataScreen
import com.example.femalepoint.presenation.screens.AllProductScreen
import com.example.femalepoint.presenation.screens.AllReviewScreen
import com.example.femalepoint.presenation.screens.CartScreen
import com.example.femalepoint.presenation.screens.GetcategoryScreen
import com.example.femalepoint.presenation.screens.LoginScreen
import com.example.femalepoint.presenation.screens.MatchingProducts
import com.example.femalepoint.presenation.screens.MyOrderScreen
import com.example.femalepoint.presenation.screens.OrderHistoryScreen
import com.example.femalepoint.presenation.screens.OrderProductScreen
import com.example.femalepoint.presenation.screens.PayMentScreen
import com.example.femalepoint.presenation.screens.ReelsVideoScreen
import com.example.femalepoint.presenation.screens.ReviewWritingAndUploadingScreen
import com.example.femalepoint.presenation.screens.SearchScreen
import com.example.femalepoint.presenation.screens.SeetingsScreen
import com.example.femalepoint.presenation.screens.SingUpScreen

import com.example.femalepoint.presenation.viewmodel.MyViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(viewModel: MyViewModel= hiltViewModel()) {
    val navController= rememberNavController()
    val auth=FirebaseAuth.getInstance().currentUser

LaunchedEffect(auth) {
    if (auth != null) {
        navController.navigate(HOMESCREEN)
        viewModel.getAllOrders()
    }
    else{
        navController.navigate(SIGNUPCREEN)
    }
}

    NavHost(navController = navController, startDestination =HOMESCREEN){
        composable<LOGINSCREEN> {
            LoginScreen(navController=navController)
        }
        composable<HOMESCREEN> {
          // LandingScreen( navController = navController)
            landingScreen(navController = navController)
        }
        composable<SIGNUPCREEN> {
          SingUpScreen(navController=navController)

        }
        composable<ORDERSCREEN> {BackStackEntery->
            val data:ORDERSCREEN=BackStackEntery.toRoute()
           OrderProductScreen(
               navController = navController,
               produt_id = data.produt_id,
               imageUri = data.imageUri,
               price = data.price,
               finalprice = data.finalprice,
               name = data.name,
               discription = data.discription,
               category = data.category,
               noOfUnits=data.noOfUnits,
           )


        }
        composable<POSTORDERSCREEN> {BackStackEntry->
            val data:POSTORDERSCREEN = BackStackEntry.toRoute()
            AddUserDataScreen(navController = navController, productname = data.productname, productimage = data.productImage
            , productcategory = data.productCategory, productinitailprice = data.initialprice,
                productfinalprice = data.finalprice,
                productID = data.produt_id,
                noOfUnitsQuntity = data.noOfUnits

            )

        }
        composable<PAYMENTSCREEN> {
            PayMentScreen()
        }
        composable<MATCHEDSCREEN> {backstackentry->
            val data:MATCHEDSCREEN=backstackentry.toRoute()
            Log.d("MATCHINGSCREEN",data.category.toString())
            MatchingProducts(category = data.category, navController = navController)
        }
        composable<ALLPRODUCTSCREEN> {
            AllProductScreen(navController = navController)
        }
        composable<ORDERHISTORYDETAILS> {backstackEntry->
            val data:ORDERHISTORYDETAILS=backstackEntry.toRoute()
            OrderHistoryScreen(
                productImage = data.imageUri,
                productCategory = data.productCategory,
                productName = data.productName, price = data.price,
               finalPrice = data.finalprice, description = data.description,
                noOfUnits = data.noOfUnits,
                productId = data.productId,
                navController = navController
            )
        }
        composable<ReviewWritingScreen> { backstackentry->
            val data:ReviewWritingScreen=backstackentry.toRoute()
            ReviewWritingAndUploadingScreen(productID = data.productID,
                imageUrl = data.imageUrl, category = data.category,
                productname = data.productname
            )
        }
        composable<ALLREVIVEWSCREEN> {bacstackentry->
            val data:ALLREVIVEWSCREEN=bacstackentry.toRoute()
            AllReviewScreen(productID = data.productId)
        }
        composable<SEARCHSCREEN> {
            SearchScreen(navController = navController)
        }
        composable<CARTSCREEN> {
            CartScreen(navController = navController)
        }
        composable<REELSVIDEOSCREEN> {
            ReelsVideoScreen(navController=navController)
        }



    }


}

data class TabItems(val title: String, val Icon: ImageVector)

@Composable
fun landingScreen(navController: NavController) {
    var selectedindex by remember { mutableStateOf(0) }
    val listOfTabs = listOf(
        TabItems(title = "Home", Icon = Icons.Default.Home),
        TabItems(title = "Orders", Icon = Icons.Default.ShoppingCart),
        TabItems(title = "Products", Icon = Icons.Default.Person),
        TabItems(title = "Search", Icon = Icons.Default.Search),
        TabItems(title = "Settings", Icon = Icons.Default.Settings)

    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                listOfTabs.forEachIndexed { index, tabItems ->
                    NavigationBarItem(
                        selected = selectedindex == index,
                        onClick = {
                            selectedindex = index
                        },
                        icon = {
                            Icon(imageVector = tabItems.Icon, contentDescription = null)
                        },
                        label = {
                            Text("${tabItems.title}")
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(selectedIndex = selectedindex, navController = navController, paddingValues = innerPadding)
    }
}

@Composable
fun ContentScreen(selectedIndex: Int, navController: NavController, paddingValues: PaddingValues) {

        when(selectedIndex){
        0->{
            GetcategoryScreen(navController=navController)
        }
        1->{
            MyOrderScreen(navController = navController)
        }
        2->{
            // MyOrderScreen()
            AllProductScreen(navController = navController)
        }
        3->{
           // CartScreen(navController = navController)
            SearchScreen(navController = navController)

        }
        4->{
            SeetingsScreen(navController = navController)
        }

    }

}

