package com.example.mvvm_practice_kotlin.network


import com.example.mvvm_practice_kotlin.model.ReqToken
import com.example.mvvm_practice_kotlin.model.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Call
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

//    companion object {
//        const val BASE_URL = "https://api.themoviedb.org/3/"
//
//
//        val gson: Gson = GsonBuilder()
//            .setDateFormat("yyyy-MM-dd HH:mm:ss")
//            .create()
//        //https://api.themoviedb.org/3/authentication/token/new?api_key=d68581f64c502852c33b6a5a88d1a589
//        //https://api.themoviedb.org/3/authentication/token/validate_with_login?api_key=d68581f64c502852c33b6a5a88d1a589
//
//        val apiService: ApiService = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(ApiService::class.java)
//    }

    @GET("authentication/token/new?api_key=d68581f64c502852c33b6a5a88d1a589")
    suspend fun getToken(): retrofit2.Response<ReqToken>

    @POST("authentication/token/validate_with_login?api_key=d68581f64c502852c33b6a5a88d1a589")
    suspend fun verifyLogin(@Body body: User): retrofit2.Response<ReqToken>
}