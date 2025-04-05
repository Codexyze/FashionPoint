package com.example.femalepoint.data.model

data class Category(
    var name:String="",
    val date:Long=System.currentTimeMillis(),
    var imageUri:String=""
)