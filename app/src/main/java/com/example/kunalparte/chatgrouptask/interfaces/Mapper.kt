package com.example.kunalparte.chatgrouptask.interfaces

interface Mapper<Entity,Model> {
    fun map(from: Entity):Model
}