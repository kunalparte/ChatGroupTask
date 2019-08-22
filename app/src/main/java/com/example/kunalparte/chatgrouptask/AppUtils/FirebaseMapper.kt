package com.example.kunalparte.chatgrouptask.AppUtils

import com.example.kunalparte.chatgrouptask.interfaces.Mapper
import com.google.firebase.database.DataSnapshot
import java.lang.reflect.ParameterizedType
import java.util.ArrayList

abstract class FirebaseMapper<Entity,Model> :Mapper<Entity,Model>{


    fun map(dataSnapshot: DataSnapshot?): Model {
        val entity = dataSnapshot!!.getValue(getEntityClass())
        return map(entity!!)
    }

    fun mapList(dataSnapshot: DataSnapshot): List<Model> {
        val list = ArrayList<Model>()
        for (item in dataSnapshot.children) {
            list.add(map(item))
        }
        return list
    }

    private fun getEntityClass(): Class<Entity> {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        return superclass.actualTypeArguments[0] as Class<Entity>
    }

}