package com.example.mvvm_practice_kotlin.viewmodell

import android.content.Context
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_practice_kotlin.model.ReqToken
import com.example.mvvm_practice_kotlin.model.User
import com.example.mvvm_practice_kotlin.network.ApiService
import com.example.mvvm_practice_kotlin.repository.AppRepository
import com.example.mvvm_practice_kotlin.utils.OnCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AppRepository): ViewModel() {

    val user = MutableLiveData<String>()
    val passw = MutableLiveData<String>()
    var userName = ""
    var password = ""

    val tokenResponse = MutableLiveData<ReqToken?>()

    fun getToken(){
        viewModelScope.launch {
            repository.getToken().let {
                if (it.isSuccessful){
                    tokenResponse.value = it.body()
                } else {
                    println("Co loi xay ra")
                }
            }
        }
    }


     fun login(token: String) = viewModelScope.launch {
        val gettoken = repository.login(userName, password, token).let {response ->
               if(response.isSuccessful){
                   user.postValue(response.body()?.requestToken) // gui reponse den user thong qua postValue
                   println("==========Login OK")
               }else{
                   Log.d("123","Error get token")
               }
           }
         }

    fun sendPost(token : String){

      }
    }

//    private val _userName2 = MutableLiveData<String>()
//    var userName2: LiveData<String> = _userName2


//    private var _userName0: MutableStateFlow<String> = MutableStateFlow("")
//    var userName: StateFlow<String> = _userName0
//
//    private var _password0: MutableStateFlow<String> = MutableStateFlow("")
//    var password: StateFlow<String> = _password0

//    private fun sendPost( token: String) {
//        val user = User("nightowl990", "123456", token)
//        ApiService.apiService.verifyLogin(user).enqueue(object : Callback<ReqToken?>{
//            override fun onResponse(call: Call<ReqToken?>, response: Response<ReqToken?>){
//                if (response.code() == 200 || response.code() == 201) {
//                    Log.e("success", response.body().toString() + " " + response.code())
//                    callBack.loginSuccess()
//                } else {
//                    Log.e("fail", response.errorBody().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<ReqToken?>, t: Throwable?) {
//            }
//        })
//    }

//    private fun getToken(){
//        ApiService.apiService.getToken().enqueue(object : Callback<ReqToken> {
//            override fun onResponse(call: Call<ReqToken>, response: Response<ReqToken>) {
//                if (response.code() == 200 || response.code() == 201){
//                    Log.e("abc", response.body().toString() + " " + response.code())
//                    sendPost(((response.body()) as ReqToken).toString())
//                } else{
//                    Log.e("abc",   "---------------${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ReqToken?>?, t: Throwable) {
//                Log.e("Errors", t.message!!)
//            }
//        })
//    }




