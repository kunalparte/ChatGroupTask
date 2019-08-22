package com.example.kunalparte.chatgrouptask.Chat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kunalparte.chatgrouptask.Chat.model.ChatMessageModel

class ChatViewModel : ViewModel() {
    var chatRepo : ChatRepo = ChatRepo()

    fun addMessage( chatMessageModel: ChatMessageModel){
        chatRepo.addMessage(chatMessageModel)
    }

    fun getChatMessages(userName : String): LiveData<List<ChatMessageModel>>{
        return chatRepo.getChatMessages(userName)
    }
}