package es.marcrdz.presentation.base

sealed class ViewEvent<out T : Event>

class UserEvent<out T :Event>(val event: T) : ViewEvent<T>()

sealed class Lifecycle : ViewEvent<Nothing>() {
    object OnCreate : Lifecycle()
    object OnCreateView : Lifecycle()
    object OnViewCreated : Lifecycle()
    object OnStart : Lifecycle()
    object OnResume : Lifecycle()
    object OnPause : Lifecycle()
    object OnStop : Lifecycle()
    object OnDestroy : Lifecycle()
}
