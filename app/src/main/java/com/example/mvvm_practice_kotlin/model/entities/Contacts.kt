package com.example.mvvm_practice_kotlin.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "numberPhone")
    val phone: String? = null
)

