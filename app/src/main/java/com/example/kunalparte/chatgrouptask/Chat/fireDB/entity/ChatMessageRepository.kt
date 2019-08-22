package com.example.kunalparte.chatgrouptask.Chat.fireDB.entity

import androidx.room.Entity
import com.example.kunalparte.chatgrouptask.AppUtils.FirebaseDatabaseRepository
import com.example.kunalparte.chatgrouptask.AppUtils.FirebaseMapper
import com.example.kunalparte.chatgrouptask.Chat.model.ChatMessageModel

class ChatMessageRepository(path : String) : FirebaseDatabaseRepository<ChatMessageModel>(ChatMessageMapper() as FirebaseMapper<Entity, ChatMessageModel>) {

    var path = path

    override fun getRootNode(): String {
        if (path != null){
            return path
        }else{
            return ""
        }
    }
}