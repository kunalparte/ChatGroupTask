package com.example.kunalparte.chatgrouptask

import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
import androidx.transition.Visibility
import java.util.prefs.Preferences
import java.util.prefs.PreferencesFactory

class AppPreferences  {

    companion object{
        private lateinit var appPreferences : AppPreferences
        private lateinit var sharedPrefs : SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        fun getInstance(context: Context): AppPreferences{
                appPreferences = AppPreferences()
                sharedPrefs = context.getSharedPreferences(AppConsts.CHAT_APP_PREFS,Context.MODE_PRIVATE)
                editor = sharedPrefs.edit()
            return appPreferences
        }
    }

    fun setLoginSuccessful(isLoginSuccessful : Boolean){
        editor.putBoolean(AppConsts.EXTRA_LOGIN, isLoginSuccessful).apply()
    }

    fun isLoginSuccesssful():Boolean{
        return sharedPrefs.getBoolean(AppConsts.EXTRA_LOGIN,false)
    }

    fun setUserKey(userKey : String){
        editor.putString(AppConsts.EXTRA_USER_KEY, userKey).apply()
    }

    fun getuserKey(): String{
        return sharedPrefs.getString(AppConsts.EXTRA_USER_KEY,"")
    }

    fun setUserNameInitials(initials : String){
        editor.putString(AppConsts.EXTRA_USER_NAME_INITIALS, initials).apply()
    }

    fun getUserNameinitals(): String{
        return sharedPrefs.getString(AppConsts.EXTRA_USER_NAME_INITIALS, "")
    }

    fun setUserEmail(initials : String){
        editor.putString(AppConsts.EXTRA_USER_EMAIL, initials).apply()
    }

    fun getUserEmails(): String{
        return sharedPrefs.getString(AppConsts.EXTRA_USER_EMAIL, "")
    }
}