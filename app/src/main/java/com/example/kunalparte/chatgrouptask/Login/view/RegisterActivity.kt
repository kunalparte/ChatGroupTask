package com.example.kunalparte.chatgrouptask.Login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.kunalparte.chatgrouptask.AppConsts
import com.example.kunalparte.chatgrouptask.AppPreferences
import com.example.kunalparte.chatgrouptask.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.toolbar

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setupToolBar()
        activity_register_btn.setOnClickListener(this)

    }

    private fun setupToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar as Toolbar?)
            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun registerUser(){
        if(AppConsts.isNetworkConnected(this)) {
            if (!TextUtils.isEmpty(activity_register_username_et.text)) {
                if (!TextUtils.isEmpty(activity_register_email_et.text)) {
                    if (!TextUtils.isEmpty(activity_register_password_et.text)) {
                        activity_register_progress_bar_layout.visibility = View.VISIBLE

                        firebaseAuth = FirebaseAuth.getInstance()
                        firebaseAuth.createUserWithEmailAndPassword(
                            activity_register_email_et.text.toString(),
                            activity_register_password_et.text.toString()
                        ).addOnCompleteListener(OnCompleteListener {
                            activity_register_progress_bar_layout.visibility = View.GONE
                            if (it.isSuccessful) {
                                AppPreferences.getInstance(this)
                                    .setUserKey(activity_register_username_et.text.toString())
                                AppPreferences.getInstance(this)
                                    .setUserNameInitials(getInitials(activity_register_username_et.text.toString()))
                                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        })
                    } else {
                        Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please Enter Email Id", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Please Connect To Internet", Toast.LENGTH_SHORT).show()
        }
    }

    fun getInitials(userNameText : String) : String{
        var initials = userNameText.substring(0, 1)
        initials = initials + userNameText.substring(userNameText.indexOf(" ")+1, userNameText.indexOf(" ")+2)
        return initials
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.activity_register_btn -> registerUser()
        }
    }
}
