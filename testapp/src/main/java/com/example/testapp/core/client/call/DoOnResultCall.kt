package com.example.testapp.core.client.call

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.testapp.core.client.utils.Result

internal class DoOnResultCall<T : Any>(
    private val originalCall: Call<T>,
    private val scope: CoroutineScope,
    private val consumer: suspend (Result<T>) -> Unit,
) : Call<T> {

    private var job: Job? = null

    override fun execute(): Result<T> = runBlocking {
        originalCall.execute().also { consumer(it) }
    }

    override fun enqueue(callback: Call.Callback<T>) {
        originalCall.enqueue { result ->
            job = scope.launch { consumer(result) }
            callback.onResult(result)
        }
    }

    override fun cancel() {
        job?.cancel()
    }
}
