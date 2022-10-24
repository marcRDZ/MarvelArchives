package es.marcrdz.presentation.base

sealed class ViewState<out T : Event>

class BackgroundState(val event: BackgroundEvent) : ViewState<Nothing>()
class FailState(val event: ErrorEvent) : ViewState<Nothing>()
class StateChange<out T : Event>(val event: T) : ViewState<T>()

sealed class BackgroundEvent : Event {
    object Idle : BackgroundEvent()
    object Loading : BackgroundEvent()
}

sealed class ErrorEvent(val timeStamp: Long) : Event {
    class Unknown : ErrorEvent(System.currentTimeMillis())
    class NoData : ErrorEvent(System.currentTimeMillis())
    class Network : ErrorEvent(System.currentTimeMillis())
    class Unauthorized : ErrorEvent(System.currentTimeMillis())
    class Forbidden : ErrorEvent(System.currentTimeMillis())
    class Exception(type: String, msg: String) : ErrorEvent(System.currentTimeMillis())
}