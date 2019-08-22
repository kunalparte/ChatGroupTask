package com.example.kunalparte.chatgrouptask.listeners

import com.example.kunalparte.chatgrouptask.AppUtils.FirebaseDatabaseRepository
import com.example.kunalparte.chatgrouptask.AppUtils.FirebaseMapper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BaseValueEventListener<Model,Entity>(
    mapper: FirebaseMapper<Entity, Model>,
    firebaseCallback: FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallBack<Model>
) : ValueEventListener {

    private val firebaseMapper : FirebaseMapper<Entity,Model> = mapper
    private val firebaseCallBack : FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallBack<Model> = firebaseCallback

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onDataChange(p0: DataSnapshot) {
        val data = firebaseMapper.mapList(p0)
        firebaseCallBack.onSuccess(data)
    }
}