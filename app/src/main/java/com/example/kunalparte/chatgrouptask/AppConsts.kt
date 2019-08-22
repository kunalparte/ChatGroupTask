package com.example.kunalparte.chatgrouptask

import android.content.Context
import android.net.ConnectivityManager

class AppConsts {

    companion object{
        var CHAT_APP_PREFS = "chatApp"
        var EXTRA_LOGIN = "login"
        var EXTRA_USER_KEY = "userKey"
        var EXTRA_USER_NAME_INITIALS = "userNameInitials"
        var EXTRA_USER_EMAIL = "userEmail"
        var PERSON_1 = 1
        var PERSON_2 = 2

        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            return cm!!.activeNetworkInfo != null
        }
    }
}