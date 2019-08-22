package com.example.kunalparte.chatgrouptask.AppUtils

import android.text.TextUtils
import androidx.room.Entity
import com.example.kunalparte.chatgrouptask.listeners.BaseValueEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

abstract class FirebaseDatabaseRepository<Model> {

    protected lateinit var databaseReference: DatabaseReference
    protected lateinit var firebaseCallback: FirebaseDatabaseRepositoryCallBack<Model>
    private lateinit var listener: BaseValueEventListener<Model,Entity>
    private lateinit var mapper: FirebaseMapper<Entity, Model>

    protected abstract fun getRootNode(): String

    constructor(mapper: FirebaseMapper<Entity, Model>) {
        if (getRootNode() != null && !TextUtils.isEmpty(getRootNode())) {
            databaseReference = FirebaseDatabase.getInstance().getReference(getRootNode())
        }
            this.mapper = mapper
    }
    fun addDataToFirebase(data : Any, databaseRef: DatabaseReference){
        databaseReference = databaseRef
        var dbKey = databaseReference.push().key
        databaseReference.child(dbKey!!).setValue(data)
    }

    fun addListener(firebaseCallback: FirebaseDatabaseRepositoryCallBack<Model>, databaseRef: DatabaseReference) {
        databaseReference = databaseRef
        this.firebaseCallback = firebaseCallback
        listener = BaseValueEventListener(mapper, firebaseCallback)
        databaseReference.addValueEventListener(listener!!)
    }

    fun removeListener() {
        databaseReference.removeEventListener(listener)
    }

    interface FirebaseDatabaseRepositoryCallBack<Any>{
        fun onSuccess(result: List<Any>)
        fun onError(e: Exception)
    }
}