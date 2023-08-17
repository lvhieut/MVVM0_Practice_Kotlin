package com.example.mvvm_practice_kotlin.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "numberPhone")
    var phone: String = "",
    @ColumnInfo(name = "viewtype")
    var viewType: Int = -1
)



