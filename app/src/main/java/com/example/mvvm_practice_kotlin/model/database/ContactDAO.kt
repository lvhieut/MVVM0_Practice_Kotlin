package com.example.mvvm_practice_kotlin.model.database

import androidx.room.*
import com.example.mvvm_practice_kotlin.model.entities.Contacts

@Dao
interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contacts)

    @Update
    suspend fun updateContact(contact: List<Contacts>)

    @Delete
    suspend fun deleteContact(contact: Contacts)

    @Query("SELECT * FROM contacts")
    suspend fun getAllContacts(): List<Contacts>

}