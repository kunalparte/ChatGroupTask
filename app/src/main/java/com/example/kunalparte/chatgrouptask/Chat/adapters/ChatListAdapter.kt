package com.example.kunalparte.chatgrouptask.Chat.adapters

import android.app.Activity
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kunalparte.chatgrouptask.AppConsts
import com.example.kunalparte.chatgrouptask.AppUtils.OnRecyclerItemClickListener
import com.example.kunalparte.chatgrouptask.Chat.model.ChatMessageModel
import com.example.kunalparte.chatgrouptask.R
import java.util.*
import kotlin.collections.ArrayList

class ChatListAdapter(email: String, onRecyclerItemClickListener: OnRecyclerItemClickListener, activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var activity = activity
    var onRecyclerItemClickListener = onRecyclerItemClickListener
    var email = email
    var messageList : List<ChatMessageModel> = ArrayList()
    var emojiDrawableList : ArrayList<Drawable> = ArrayList()
    var prevPosition : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == AppConsts.PERSON_1){
            return Participant1VHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_participant_1_layout,parent,false))
        }else{
            return Participant2VHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_participant_2_layout,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (messageList.get(position).name.equals(email)){
            return AppConsts.PERSON_1
        }else{
            return AppConsts.PERSON_2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            AppConsts.PERSON_1 -> setDataForParticipant1(holder as Participant1VHolder,position)
            AppConsts.PERSON_2 -> setDataForParticipant2(holder as Participant2VHolder,position)
        }
    }

    fun setDataForParticipant1(participant1VHolder: Participant1VHolder,position: Int){
        participant1VHolder.participant1TimeStampTV.setText(getFormattedTime(""+messageList.get(position).timeStamp))
        participant1VHolder.participant1MessageTV.setText(messageList.get(position).messageText)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getFormattedTime(timeStamp : String): String? {
        if (!TextUtils.isEmpty(timeStamp)) {
            val sdf = SimpleDateFormat("HH:mm")
            val currenTimeZone = Calendar.getInstance().getTime() as Date
            return sdf.format(currenTimeZone)
        }
        return ""
    }

    fun setDataForParticipant2(participant2VHolder: Participant2VHolder,position: Int){
        participant2VHolder.participant2TimeStampTV.setText(getFormattedTime(""+messageList.get(position).timeStamp))
        participant2VHolder.participant2MessageTV.setText(messageList.get(position).messageText)
        participant2VHolder.participant2InitialsTV.setText(messageList.get(position).initials)
        participant2VHolder.participant2NameTV.setText(messageList.get(position).name)
        participant2VHolder.participant2ImageBtn.setOnClickListener(View.OnClickListener {
            setEmojiLayout(participant2VHolder,participant2VHolder.adapterPosition)
        })
    }

    fun setEmojiDrawableList(){
        emojiDrawableList = ArrayList()
        emojiDrawableList.add(activity.getDrawable(R.drawable.happy))
        emojiDrawableList.add(activity.getDrawable(R.drawable.happy_2))
        emojiDrawableList.add(activity.getDrawable(R.drawable.smiling))
        emojiDrawableList.add(activity.getDrawable(R.drawable.smiling))
    }

    fun setEmojiLayout(participant2VHolder: Participant2VHolder,position: Int) {
        if (prevPosition == position) {
            participant2VHolder.emojiParentLayoutHolder.visibility = View.GONE
            participant2VHolder.participant2ImageBtn.isSelected = false
        } else{
            if (participant2VHolder.participant2ImageBtn.isSelected){
                participant2VHolder.emojiParentLayoutHolder.visibility = View.GONE
                participant2VHolder.participant2ImageBtn.isSelected = false
            }
            setEmojiDrawableList()
        participant2VHolder.emojiParentLayoutHolder.visibility = View.VISIBLE
        participant2VHolder.emojiParentLayout.removeAllViews()
        for (image in emojiDrawableList) {
            var view =
                activity.layoutInflater.inflate(R.layout.emoji_layout, participant2VHolder.emojiParentLayout, false)
            view.findViewById<ImageView>(R.id.emoji_image_view).setImageDrawable(image)
            participant2VHolder.emojiParentLayout.addView(view)
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up_annimation))
        }
        participant2VHolder.emojiParentLayoutHolder.setCardBackgroundColor(android.R.color.transparent)
        participant2VHolder.emojiParentLayoutHolder.bringToFront()
            participant2VHolder.participant2ImageBtn.isSelected = true
        prevPosition = position
    }
    }

    class Participant1VHolder(view: View) : RecyclerView.ViewHolder(view) {
        var view = view
        var participant1TimeStampTV = view.findViewById<TextView>(R.id.chat_participant_1_timestamp_tv)
        var participant1MessageTV = view.findViewById<TextView>(R.id.chat_participant_1_message_tv)
    }

    class Participant2VHolder(view: View) : RecyclerView.ViewHolder(view){
        var view = view
        var participant2TimeStampTV = view.findViewById<TextView>(R.id.chat_participant_2_timestamp_tv)
        var participant2MessageTV = view.findViewById<TextView>(R.id.chat_participant_2_message_tv)
        var participant2InitialsTV = view.findViewById<TextView>(R.id.chat_participant_2_initials_tv)
        var participant2NameTV = view.findViewById<TextView>(R.id.chat_participant_2_name_tv)
        var participant2ImageBtn = view.findViewById<ImageButton>(R.id.chat_participant_2_smiley_btn)
        var emojiParentLayoutHolder = view.findViewById<CardView>(R.id.emoji_parent_layout_holder)
        var emojiParentLayout = view.findViewById<LinearLayout>(R.id.emoji_parent_layout)
    }
}