package com.example.mvvm_practice_kotlin

import android.app.Application
import androidx.room.Room
import com.example.mvvm_practice_kotlin.model.database.ContacsDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object {
        lateinit var database: ContacsDatabase
        lateinit var instance: App
    }

    fun getDb(): ContacsDatabase{
        return getDb()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database =  Room.databaseBuilder(applicationContext, ContacsDatabase::class.java, "contacts").build()
    }
}