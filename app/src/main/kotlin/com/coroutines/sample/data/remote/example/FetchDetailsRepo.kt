package com.coroutines.sample.data.remote.example

import com.coroutines.sample.data.remote.model.Resource
import com.coroutines.sample.data.remote.model.example.DogDetailsResponse
import com.coroutines.sample.extensions.handleException

class FetchDetailsRepo(private val fetchDetailsAPI: FetchDetailsAPI) {
    suspend fun fetchDetails(): Resource<DogDetailsResponse> {
        return handleException { fetchDetailsAPI.fetchDetails() }
    }
    suspend fun mockErrorResponse(id: Int): Resource<DogDetailsResponse> {
        return handleException { fetchDetailsAPI.mockErrorResponse(id) }
    }
}