package com.example.kunalparte.chatgrouptask.Login.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.kunalparte.chatgrouptask.AppConsts
import com.example.kunalparte.chatgrouptask.AppPreferences
import com.example.kunalparte.chatgrouptask.Chat.viewmodel.ChatViewModel
import com.example.kunalparte.chatgrouptask.Chat.views.ChatActivity
import com.example.kunalparte.chatgrouptask.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!AppPreferences.getInstance(this).isLoginSuccesssful()) {
            FirebaseApp.initializeApp(applicationContext);
            firebaseAuth = FirebaseAuth.getInstance()
            activity_login_btn.setOnClickListener(this)
            activity_login_register_btn.setOnClickListener(this)
        }else{
            openChatActivity()
        }
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.activity_login_register_btn -> opentRegisterActivity()
            R.id.activity_login_btn -> signIn()
        }
    }

    fun opentRegisterActivity(){
        var intent : Intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

    fun openChatActivity(){
        startActivity(Intent(this,ChatActivity::class.java))
        finish()
    }

    fun signIn() {
        if (AppConsts.isNetworkConnected(this)) {
            if (!TextUtils.isEmpty(activity_login_email_et.text)) {
                if (!TextUtils.isEmpty(activity_login_password_et.text)) {
                    activity_login_progress_bar_layout.visibility = View.VISIBLE
                    firebaseAuth.signInWithEmailAndPassword(
                        activity_login_email_et.text.toString(),
                        activity_login_password_et.text.toString()
                    ).addOnCompleteListener(this, OnCompleteListener {
                        activity_login_progress_bar_layout.visibility = View.GONE
                        if (it.isSuccessful) {
                                AppPreferences.getInstance(this).setUserEmail(activity_login_email_et.text.toString())
                                AppPreferences.getInstance(this).setLoginSuccessful(true)
                                openChatActivity()
                            } else {
                                AppPreferences.getInstance(this).setLoginSuccessful(false)
                                Toast.makeText(this, "Please Enter Valid Credentials", Toast.LENGTH_SHORT).show()
                            }
                        })
                } else {
                    Toast.makeText(this, "Please Enter password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter email id", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_SHORT).show()
        }
    }
}
