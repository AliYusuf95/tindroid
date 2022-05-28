package com.example.testapp.core.internal.coroutines

import com.example.testapp.core.internal.InternalTinUiApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Coroutine dispatchers used internally. Should always be used
 * instead of directly using [Dispatchers] or creating new dispatchers.
 *
 * Can be modified using [set] and [reset] for testing purposes.
 */
@InternalTinUiApi
public object DispatcherProvider {
    public var Main: CoroutineDispatcher = Dispatchers.Main
        internal set

    public var IO: CoroutineDispatcher = Dispatchers.IO
        internal set

    /**
     * Overrides the main (UI thread) and IO dispatcher. For testing purposes only.
     */
    public fun set(mainDispatcher: CoroutineDispatcher, ioDispatcher: CoroutineDispatcher) {
        Main = mainDispatcher
        IO = ioDispatcher
    }

    /**
     * Resets the dispatchers to their default values. For testing purposes only.
     */
    public fun reset() {
        Main = Dispatchers.Main
        IO = Dispatchers.IO
    }

}