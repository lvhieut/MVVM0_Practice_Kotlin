package com.example.mvvm_practice_kotlin.model

import com.example.mvvm_practice_kotlin.model.entities.Contacts

data class NameAlphabet(
    val nameAlphabet: String,
    val person: List<Contacts>
)