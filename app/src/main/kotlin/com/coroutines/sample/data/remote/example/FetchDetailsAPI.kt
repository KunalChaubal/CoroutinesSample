package com.coroutines.sample.data.remote.example

import com.coroutines.sample.data.remote.model.example.DogDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FetchDetailsAPI {
        @GET("/api/breeds/image/random")
        suspend fun fetchDetails(): DogDetailsResponse

        @GET("/api/breeds/image/{id}/error")
        suspend fun mockErrorResponse(@Path(value = "id", encoded = true) id: Int): DogDetailsResponse
}