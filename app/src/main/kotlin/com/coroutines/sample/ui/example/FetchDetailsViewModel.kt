package com.coroutines.sample.ui.example

import android.util.Log
import androidx.lifecycle.ViewModel
import com.coroutines.sample.data.remote.example.FetchDetailsRepo
import com.coroutines.sample.data.remote.model.Resource
import com.coroutines.sample.livedata.SingleLiveEvent
import com.coroutines.sample.manager.CoroutinesManager
import kotlinx.coroutines.*
import java.lang.Exception

class FetchDetailsViewModel(
    private val coroutinesManager: CoroutinesManager,
    private val fetchDetailsRepo: FetchDetailsRepo
) : ViewModel() {

    companion object {
        private const val logTag = "CoroutinesSample"
    }

    val updateEvent = SingleLiveEvent<Boolean>()

    fun tryAsyncNetworkCall() {
        Log.i(logTag, "-----Async network calls without error handling-----")
        coroutinesManager.ioScope.launch {
            var job: Job? = null

            Log.i(logTag, "Making 10 asynchronous network calls")
            for (i in 0..10) {
                job = launch {
                    Log.i(logTag, "Network Call ID: $i")
                    fetchDetailsRepo.fetchDetails()
                }
            }

            job?.join()
            Log.i(logTag, "All Networks calls have completed executing")
        }
    }

    fun tryExceptionHandler() {
        val deferredList = ArrayList<Deferred<*>>()
        val errorHandler = CoroutineExceptionHandler { _, error ->
            when (error) {
                is CustomException -> {
                    Log.i(logTag, "Network call ${error.errorDescription} failed. Canceling all pending jobs")
                    Log.i(logTag, "Update UI with Error handling")
                }
                else -> Log.i(logTag, "Exception ${error.localizedMessage}")
            }

            showDeferredListLogs(deferredList)
        }
        val errorHandlingScope = CoroutineScope(errorHandler)

        errorHandlingScope.launch {
            Log.i(logTag, "-----Cancel all network calls if any one fails-----")

            Log.i(logTag, "Making 10 concurrent network calls")
            for (i in 0..9) {
                when (i) {
                    4 -> deferredList.add(async {
                        throwCustomException(i)
                    })
                    else -> deferredList.add(async {
                        fetchDetailsRepo.fetchDetails()
                    })
                }
            }
            deferredList.awaitAll()
            Log.i(logTag, "All Networks calls have completed executing")

            updateEvent.postValue(true)
            Log.i(logTag, "Update UI")
        }
    }

    fun trySupervisorScope() {
        coroutinesManager.ioScope.launch {
            Log.i(logTag, "-----Continue execution even if any network call fails-----")
            supervisorScope {
                val deferredList = ArrayList<Deferred<*>>()

                Log.i(logTag, "Making 10 concurrent network calls")
                for (i in 0..9) {
                    when (i) {
                        4 -> deferredList.add(async {
                            simulateFailedNetworkCall(i)
                        })
                        else -> deferredList.add(async {
                            fetchDetailsRepo.fetchDetails()
                        })
                    }
                }

                deferredList.joinAll()

                val cancelledCallList = deferredList.filter { it.isCancelled }
                cancelledCallList.forEach {
                    when (val exception = it.getCompletionExceptionOrNull()) {
                        is CustomException -> {
                            Log.i(logTag, "Failed API Call ID: ${exception.errorDescription}")
                        }
                    }
                }

                updateEvent.postValue(true)
                Log.i(logTag, "Update UI")

                showDeferredListLogs(deferredList)
            }
        }
    }

    fun tryAdvancedSupervisorScope() {
        coroutinesManager.cancelableScope.launch {
            Log.i(logTag, "-----Depending upon the network response, continue or cancel pending jobs-----")
            supervisorScope {
                val deferredList = ArrayList<Deferred<*>>()

                Log.i(logTag, "Making 10 concurrent network calls")
                for (i in 0..9) {
                    when (i) {
                        4, 7 -> deferredList.add(async {
                            simulateFailedNetworkCall(i)
                        })
                        else -> deferredList.add(async {
                            fetchDetailsRepo.fetchDetails()
                        })
                    }
                }

                try {
                    deferredList.joinAll()
                    Log.i(logTag, "All Networks calls have completed executing")

                    updateEvent.postValue(true)
                    Log.i(logTag, "Update UI")
                } catch (exception: Exception) {
                    Log.i(logTag, "Not all API calls succeeded. Canceling all pending jobs. Update UI with error handling")
                }

                showDeferredListLogs(deferredList)
            }
        }
    }

    private fun showDeferredListLogs(deferredList: ArrayList<Deferred<*>>) {
        Log.i(logTag, "------------------------")
        deferredList.forEachIndexed { index, deferred ->
            Log.i(logTag, "DeferredJobList = Index: $index, Cancelled: ${deferred.isCancelled}")
        }
        Log.i(logTag, "------------------------")
    }

    private suspend fun simulateFailedNetworkCall(id: Int): Resource<*> {
        val response = fetchDetailsRepo.mockErrorResponse(id)
        when (response.status) {
            Resource.Status.ERROR -> {
                when (id) {
                    4 -> {
                        Log.i(logTag, "Network call $id failed. Continue all pending jobs")
                        throw CustomException("$id")
                    }
                    7 -> {
                        Log.i(logTag, "Network call $id failed. Canceling all pending jobs")
                        coroutinesManager.cancelableScope.cancel()
                    }
                }
            }
            else -> {
            }
        }
        return response
    }

    private suspend fun throwCustomException(id: Int): Resource<*> {
        val response = fetchDetailsRepo.mockErrorResponse(id)
        when (response.status) {
            Resource.Status.ERROR -> {
                throw CustomException("$id")
            }
            else -> {
            }
        }
        return response
    }
}

class CustomException(val errorDescription: String): Exception()