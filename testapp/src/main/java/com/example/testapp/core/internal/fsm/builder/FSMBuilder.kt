package com.example.testapp.core.internal.fsm.builder

import com.example.testapp.core.internal.InternalTinUiApi
import com.example.testapp.core.internal.fsm.FiniteStateMachine
import com.example.testapp.core.internal.fsm.StateFunction
import kotlin.reflect.KClass

@InternalTinUiApi
@FSMBuilderMarker
public class FSMBuilder<S : Any, E : Any> {
    private lateinit var _initialState: S
    public val stateFunctions: MutableMap<KClass<out S>, Map<KClass<out E>, StateFunction<S, E>>> = mutableMapOf()
    private var _defaultHandler: (S, E) -> Unit = { _, _ -> Unit }

    @FSMBuilderMarker
    public fun initialState(state: S) {
        _initialState = state
    }

    @FSMBuilderMarker
    public fun defaultHandler(defaultHandler: (S, E) -> Unit) {
        _defaultHandler = defaultHandler
    }

    @FSMBuilderMarker
    public inline fun <reified S1 : S> state(stateHandlerBuilder: StateHandlerBuilder<S, E, S1>.() -> Unit) {
        stateFunctions[S1::class] = StateHandlerBuilder<S, E, S1>().apply(stateHandlerBuilder).get()
    }

    internal fun build(): FiniteStateMachine<S, E> {
        check(this::_initialState.isInitialized) { "Initial state must be set!" }
        return FiniteStateMachine(_initialState, stateFunctions, _defaultHandler)
    }
}
