package com.example.kunalparte.chatgrouptask.Chat.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kunalparte.chatgrouptask.AppPreferences
import com.example.kunalparte.chatgrouptask.Chat.adapters.ChatListAdapter
import com.example.kunalparte.chatgrouptask.Chat.model.ChatMessageModel
import com.example.kunalparte.chatgrouptask.Chat.viewmodel.ChatViewModel
import com.example.kunalparte.chatgrouptask.R
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*
import android.net.Uri
import android.view.MotionEvent
import android.widget.Toast
import com.example.kunalparte.chatgrouptask.AppUtils.OnRecyclerItemClickListener


class ChatActivity : AppCompatActivity(), View.OnClickListener, OnRecyclerItemClickListener{

    lateinit var chatViewModel: ChatViewModel
    lateinit var chatListAdapter: ChatListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
        setRecyclerView()
        setDataOnRecyclerView()
        setChatEditTextAndSendBtns()
    }

    fun init(){
        setupToolBar()
        activity_chat_progress_bar_layout.visibility = View.VISIBLE
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        chatListAdapter = ChatListAdapter(AppPreferences.getInstance(this).getuserKey(), this, this)
        activity_chat_camera_btn.setOnClickListener(this)
        activity_chat_record_send_btn.setOnClickListener(this)

    }


    private fun setupToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun setRecyclerView(){
        chatListReccyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = chatListAdapter
            it.setHasFixedSize(true)
        }
    }

    fun setDataOnRecyclerView(){
        chatViewModel.getChatMessages(AppPreferences.getInstance(this).getuserKey()).observe(this,object : Observer<List<ChatMessageModel>>{
            override fun onChanged(t: List<ChatMessageModel>?) {
                if (t != null) {
                    if (t.size > 0){
                        if (activity_chat_progress_bar_layout.visibility == View.VISIBLE) {
                            activity_chat_progress_bar_layout.visibility = View.GONE
                        }
                        chatListAdapter.messageList = t
                        chatListReccyclerView.smoothScrollToPosition(chatListAdapter.messageList.size-1)
                        chatListAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    fun setChatEditTextAndSendBtns(){
        activity_chat_message_et.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.length > 1){
                        activity_chat_record_send_btn.setImageResource(R.drawable.ic_send_black_24dp)
                    }else{
                        activity_chat_record_send_btn.setImageResource(R.drawable.ic_settings_voice_black_24dp)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    fun addMessage(){
        if (activity_chat_message_et.text.length > 0) {
            var message: ChatMessageModel = ChatMessageModel()
            message.name = AppPreferences.getInstance(this).getuserKey()
            message.initials = AppPreferences.getInstance(this).getUserNameinitals()
            message.email = AppPreferences.getInstance(this).getUserEmails()
            message.messageText = activity_chat_message_et.text.toString()
            var calendar: Calendar = Calendar.getInstance()
            message.timeStamp = calendar.timeInMillis
            chatViewModel.addMessage(message)
            activity_chat_message_et.setText("")
            activity_chat_record_send_btn.setImageResource(R.drawable.ic_settings_voice_black_24dp)
        }else{
            openRecorder()
        }
    }


    fun openCaamera(){
        startActivity(Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA))
    }

    fun openVideoCamera(){
        startActivity(Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA))
    }

    fun openDialer(){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:9892377382")
        startActivity(intent)
    }

    fun openRecorder(){
//        startActivity(Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION))
        Toast.makeText(this,"Recorder To be Implemented", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.activity_chat_camera_btn -> openCaamera()
            R.id.activity_chat_record_send_btn -> addMessage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_chat_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.activity_chat_menu_video_cam -> openVideoCamera()
            R.id.activity_chat_menu_phone -> openDialer()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRecyclerItemClick(position: Int) {

    }

    override fun onItemChildClickListener(view: Any,view2: Any) {

    }

}
