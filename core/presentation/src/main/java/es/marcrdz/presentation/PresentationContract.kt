package es.marcrdz.presentation

import es.marcrdz.presentation.base.Event
import es.marcrdz.presentation.base.UserEvent
import es.marcrdz.presentation.base.ViewEvent
import es.marcrdz.presentation.base.ViewState
import kotlinx.coroutines.flow.Flow

interface PresentationContract {

    interface EventHandler<in I : ViewEvent<Event>, out O : ViewState<Event>> {

        suspend fun handleInit(onEvent: suspend (O) -> Unit) {}

        suspend fun handleClear(onDataEvent: suspend (O) -> Unit) {}

        suspend fun handleEvent(viewEvent: I, onEvent: suspend (O) -> Unit) {}
    }

    interface EventFlowHandler<in I : UserEvent<Event>, out O : ViewState<Event>> {

        suspend fun handleInit(): Flow<O>

        suspend fun handleClear(): Flow<O>

        suspend fun handleEvent(viewEvent: I): Flow<O>

    }

}
