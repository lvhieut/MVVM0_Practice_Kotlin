package com.example.mvvm_practice_kotlin.utils

import com.example.mvvm_practice_kotlin.model.entities.Contacts

interface OnContactAddedListener {
    fun onContactAdded(contacts: Contacts)
}