package es.marcrdz.ui.base

import androidx.lifecycle.ViewModel
import es.marcrdz.presentation.PresentationContract
import es.marcrdz.presentation.base.Event
import es.marcrdz.presentation.base.UserEvent
import es.marcrdz.presentation.base.ViewState

abstract class BaseViewModel<E : Event, D : Event, SH: BaseStateHolder<D>>(
    protected val handler: PresentationContract.EventFlowHandler<UserEvent<E>, ViewState<D>>
) : ViewModel() {

    abstract val stateHolder: SH

    abstract fun processUserEvent(viewEvent: UserEvent<E>)
}
