package com.example.mvvm_practice_kotlin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ReqToken (
     val success: Boolean,
     @SerializedName("expires_at")
     var expiresAt: String,
     @SerializedName("request_token")
     var requestToken: String
)

