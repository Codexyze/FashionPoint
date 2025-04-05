package com.example.femalepoint.presenation.LocalNotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.femalepoint.R
import com.example.femalepoint.constants.Constants
import kotlin.random.Random

fun createChannel(context: Context){
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
       val id=Constants.CHANNEL_ID
        val name=Constants.CHANNEL_NAME
        val channel= NotificationChannel(id,name, NotificationManager.IMPORTANCE_HIGH)
        context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

    }
}
fun pushPaymentSucessfulNotification(context: Context){
    val notification = NotificationCompat.Builder(context,Constants.CHANNEL_ID)
        .setContentTitle(Constants.APPNAME).
        setContentText(Constants.AFTERPAYMENTNOTIFICATION).setSmallIcon(R.mipmap.ic_launcher)
        .build()
    context.getSystemService(NotificationManager::class.java).notify(
        Random.nextInt(),notification
    )

}