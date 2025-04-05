package com.example.femalepoint.domain.common

sealed class ResultState<out t>{
    data class Sucess<T>(val data:T):ResultState<T>()
    data class Error(val message:String):ResultState<Nothing>()
    object Loading:ResultState<Nothing>()
}