package com.example.mvvm_practice_kotlin.utils

import android.util.Log
import android.widget.Toast
import com.example.mvvm_practice_kotlin.network.ApiService
import dagger.Component
import java.text.Format
import javax.inject.Inject


interface OnCallBack {
    fun loginSuccess()
    fun notNullEmail()
    fun NotNullPassword()
    fun FormatEmail()
    fun FormatPassword()
    fun InvalidEmail()
    fun setNavigationVisibility(isVisible: Boolean)
//    fun InvalidPassword()
}
