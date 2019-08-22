package com.example.kunalparte.chatgrouptask.Chat.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class ChatMessageModel(var name : String) : Parcelable,Serializable{

    constructor() : this("") {

    }
    var initials : String = ""
    var timeStamp : Long = 0
    var messageText : String = ""
    var email : String = ""

    constructor(parcel: Parcel) : this(parcel.readString()) {
        initials = parcel.readString()
        timeStamp = parcel.readLong()
        messageText = parcel.readString()
        email = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(initials)
        parcel.writeLong(timeStamp)
        parcel.writeString(messageText)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChatMessageModel> {
        override fun createFromParcel(parcel: Parcel): ChatMessageModel {
            return ChatMessageModel(parcel)
        }

        override fun newArray(size: Int): Array<ChatMessageModel?> {
            return arrayOfNulls(size)
        }
    }
}