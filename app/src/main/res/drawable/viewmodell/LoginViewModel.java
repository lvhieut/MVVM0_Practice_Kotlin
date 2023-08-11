package com.example.mvvm_practice.viewmodell;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.example.mvvm_practice.BR;
import com.example.mvvm_practice.model.ReqToken;
import com.example.mvvm_practice.model.User;
import com.example.mvvm_practice.network.ApiService;
import com.example.mvvm_practice.network.OnCallBack;
import com.example.mvvm_practice.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseObservable {
    public OnCallBack callBack;

    public void setCallBack(OnCallBack callBack) {
        this.callBack = callBack;
    }

    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    public void onClickLogin(String user){
        getToken();
//        if(mlistUser == null || mlistUser.isEmpty()){
//            return;
//        }
//        boolean isUserInCom = false;
//        for(User user : mlistUser){
//            if(email.equals(user.getUserEmail()) && password.equals(user.getUserPassword())){
//               isUserInCom = true;
//               break;
//            }
//        }
//        if (isUserInCom){
//           Log.i(TAG1, "");
//        }
//        else {
//            Log.i(TAG, "");
//        }
    }

    private void sendPost(String username, String pass, String token) {
        User user = new User(username, pass, token);
        ApiService.apiService.verifyLogin(user).enqueue(new Callback<ReqToken>() {
            @Override
            public void onResponse(Call<ReqToken> call, Response<ReqToken> response) {
                if (response.code() == 200 || response.code() == 201){
                    Log.e("abc", response.body() + " "+ response.code());
                    callBack.loginSuccess();
                } else {
                    Log.e("abc", response.errorBody() + "");
                }
            }

            @Override
            public void onFailure(Call<ReqToken> call, Throwable t) {

            }
        });
    }

    private void getToken(){
        ApiService.apiService.getToken()
                .enqueue(new Callback<ReqToken>() {
                    @Override
                    public void onResponse(@NonNull Call<ReqToken> call,@NonNull Response<ReqToken> response) {
                        Log.e("abc", response.body() + " "+ response.code());
                        sendPost(username.get(), password.get(), (response.body()).toString());
                    }

                    @Override
                    public void onFailure(Call<ReqToken> call, Throwable t) {
                        Log.i("Errors",t.getMessage());
                    }
                });
    }
}
