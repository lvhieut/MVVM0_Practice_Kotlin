package com.example.mvvm_practice_kotlin.model.database

import androidx.room.*
import com.example.mvvm_practice_kotlin.model.entities.Contacts

@Dao
interface ContactDAO {
    @Insert
    fun insertContact(contact: Contacts)

    @Update
    fun updateContact(contact: Contacts)

    @Delete
    fun deleteContact(contact: Contacts)

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<Contacts>
}