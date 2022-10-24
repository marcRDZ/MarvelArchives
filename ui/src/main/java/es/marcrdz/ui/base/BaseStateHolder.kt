package es.marcrdz.ui.base

import es.marcrdz.presentation.base.*
import kotlinx.coroutines.flow.*

abstract class BaseStateHolder<in T : Event> {
    private val _backgroundState: MutableStateFlow<BackgroundEvent> by lazy { MutableStateFlow(BackgroundEvent.Idle) }
    val backgroundState: StateFlow<BackgroundEvent>
        get() = _backgroundState.asStateFlow()
    private val _failState: MutableSharedFlow<ErrorEvent> by lazy { MutableSharedFlow() }
    val failState: SharedFlow<ErrorEvent>
        get() = _failState.asSharedFlow()

    protected abstract suspend fun emitStateChange(event: T)

    suspend fun emitViewState(state: ViewState<T>) {
        when(state) {
            is BackgroundState -> _backgroundState.emit(state.event)
            is FailState -> _failState.emit(state.event)
            is StateChange -> emitStateChange(state.event)
        }
    }

}
