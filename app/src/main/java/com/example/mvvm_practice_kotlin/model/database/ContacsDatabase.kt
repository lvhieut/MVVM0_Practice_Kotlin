package com.example.mvvm_practice_kotlin.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvm_practice_kotlin.model.entities.Contacts

@Database(entities = [Contacts::class], version = 1)
abstract class ContacsDatabase: RoomDatabase() {
    abstract fun contactDao() : ContactDAO
}