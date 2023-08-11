package com.example.mvvm_practice_kotlin.repository

import com.example.mvvm_practice_kotlin.model.ReqToken
import com.example.mvvm_practice_kotlin.model.User
import com.example.mvvm_practice_kotlin.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AppRepository @Inject constructor(private val api: ApiService) {

    suspend fun getToken(): Response<ReqToken> = withContext(Dispatchers.IO) {
       api.getToken()
    }

    suspend fun login(userName: String, userPassword: String, token: String): Response<ReqToken> = withContext(Dispatchers.IO) {
        api.verifyLogin(User(userName, userPassword, token))

    }


}