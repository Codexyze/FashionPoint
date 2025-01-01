package com.example.femalepoint.navigation

import com.example.femalepoint.data.Product
import kotlinx.serialization.Serializable

@Serializable
object SIGNUPCREEN

@Serializable
object LOGINSCREEN

@Serializable
object HOMESCREEN

@Serializable
data class ORDERSCREEN(val produt_id:String,val imageUri:String,val price:Int,val finalprice:Int,
                      val name:String, val discription:String,val category:String,
                      val noOfUnits:Int)
@Serializable
data class POSTORDERSCREEN(
 val produt_id: String,
   val  productname: String,
   val  productImage: String,
    val initialprice:String,
    val finalprice:String,
   val  productCategory:String,
    val noOfUnits:Int
)
@Serializable
object PAYMENTSCREEN


@Serializable
data class MATCHEDSCREEN(val category:String)

@Serializable
object  ALLPRODUCTSCREEN

@Serializable
data class ORDERHISTORYDETAILS(val productCategory: String,val productName: String,
    val price: Int,val finalprice: Int,val description: String,val noOfUnits: Int,
                               val productId: String,val imageUri:String
    )

@Serializable
data class ReviewWritingScreen(val productID: String,val imageUrl: String,val category: String,val productname: String)

@Serializable
data class ALLREVIVEWSCREEN(val productId: String)

@Serializable
object SEARCHSCREEN

