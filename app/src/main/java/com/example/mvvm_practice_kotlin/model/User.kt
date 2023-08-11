package com.example.mvvm_practice_kotlin.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class User(
    val userEmail: String,
    val userPassword: String,
    val regTok: String
)



