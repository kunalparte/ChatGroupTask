package com.example.kunalparte.chatgrouptask.Chat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kunalparte.chatgrouptask.AppUtils.FirebaseDatabaseRepository
import com.example.kunalparte.chatgrouptask.Chat.fireDB.entity.ChatMessageRepository
import com.example.kunalparte.chatgrouptask.Chat.model.ChatMessageModel
import com.google.firebase.database.FirebaseDatabase

class ChatRepo {

    var databaseRef = FirebaseDatabase.getInstance().getReference("chatgroup")
    var chatListLiveData : MutableLiveData<List<ChatMessageModel>> = MutableLiveData()
    lateinit var chatMessageRepository: ChatMessageRepository


    fun addMessage(message: ChatMessageModel){
            var path = databaseRef.toString()
            chatMessageRepository = ChatMessageRepository(path)
            chatMessageRepository.addDataToFirebase(message, databaseRef)
    }


    fun getChatMessages(userName : String): LiveData<List<ChatMessageModel>>{
        loadChatMessages(userName)
        return chatListLiveData
    }

    fun loadChatMessages(userName : String){
        var path = databaseRef.toString()
        chatMessageRepository = ChatMessageRepository(path)
        chatListLiveData = MutableLiveData()
        chatMessageRepository.addListener(object : FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallBack<ChatMessageModel>{
            override fun onSuccess(result: List<ChatMessageModel>) {
                if (result.size > 0){
                    chatListLiveData.postValue(result)
                }
            }

            override fun onError(e: Exception) {

            }

        }, databaseRef)
    }
}