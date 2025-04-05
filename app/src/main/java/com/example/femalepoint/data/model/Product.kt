package com.example.femalepoint.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val name: String="",
    val date:Long=0L,
    val imageUri: String="",
    val category: String="",
    val price : Int=0,
    val finalprice : Int=0,
    val description : String="",
    val isAvaliable : Boolean=true,
    val noOfUnits: Int=0,
    val productid : String=""
)