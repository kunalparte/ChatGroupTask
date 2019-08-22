package com.example.kunalparte.chatgrouptask.Login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.kunalparte.chatgrouptask.AppPreferences
import com.example.kunalparte.chatgrouptask.Chat.views.ChatActivity
import com.example.kunalparte.chatgrouptask.R
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val slide_up = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_up_annimation
        )

        activity_splash_logo_layout.startAnimation(slide_up)

        var handler :Handler = Handler()
        handler.postDelayed(Runnable {
            runOnUiThread {
                if (AppPreferences.getInstance(this).isLoginSuccesssful()){
                    openChatActivity()
                }else{
                    openLoginActivity()
                }
                finish()
            }
        },3000)
    }


    fun openLoginActivity(){
        startActivity(Intent(this,LoginActivity::class.java))
    }

    fun openChatActivity(){
        startActivity(Intent(this,ChatActivity::class.java))
    }
}
