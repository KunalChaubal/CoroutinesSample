package com.coroutines.sample.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

class CoroutinesManager {

    private val supervisorJob = SupervisorJob()

    private val job = Job()

    /**
     * This is a scope for all coroutines
     * that will be dispatched in a Thread Pool
     */
    val ioScope = CoroutineScope(Dispatchers.IO + job )
    /**
     * This is a scope for all coroutines
     * that will be dispatched in a Thread Pool
     */
    val cancelableScope = CoroutineScope(Dispatchers.IO + job )
}