package com.example.femalepoint.data.model

data class OrderDetails(
    val productID:String="",
    val address:String="" ,
    val phonenumber:String = "",
    val phonenumber2: String = "",
    val pincode:String  ="",
    val state:String = "",
    val nearbyPoints:String ="",
    val productname:String ="",
    val productCategory:String ="",
    val productImage:String ="",
    val productinitialprice:String ="",
    val productFinalPrice:String ="",
    val noOfproducts:Int=0,
    //added date
    val date:Long=0L
)