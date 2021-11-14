package net.flow9.thisiskotlin.firebasechat

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        val shared = getSharedPreferences("firebase", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.putString("fcm_token", token)
        editor.apply()
    }
}
