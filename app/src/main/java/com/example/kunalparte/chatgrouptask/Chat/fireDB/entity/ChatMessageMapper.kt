package com.example.kunalparte.chatgrouptask.Chat.fireDB.entity

import com.example.kunalparte.chatgrouptask.AppUtils.FirebaseMapper
import com.example.kunalparte.chatgrouptask.Chat.model.ChatMessageModel

class ChatMessageMapper : FirebaseMapper<ChatMessageEntity, ChatMessageModel>() {

    override fun map(from: ChatMessageEntity): ChatMessageModel {
        val chatMessageModel : ChatMessageModel = ChatMessageModel()
        chatMessageModel.name = from.name
        chatMessageModel.initials = from.initials
        chatMessageModel.timeStamp = from.timeStamp
        chatMessageModel.messageText = from.messageText
        return chatMessageModel
    }
}