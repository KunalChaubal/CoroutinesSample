package com.coroutines.sample.data.remote.model.example


import com.google.gson.annotations.SerializedName

data class DogDetailsResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)