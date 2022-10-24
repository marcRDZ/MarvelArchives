package es.marcrdz.presentation.handlers.main

import es.marcrdz.domain.domain.CharacterDO
import es.marcrdz.domain.domain.ResultDO
import es.marcrdz.domain.usecases.FetchCharactersUseCase
import es.marcrdz.domain.usecases.UseCase
import es.marcrdz.presentation.PresentationContract
import es.marcrdz.presentation.base.*
import es.marcrdz.presentation.mappers.toErrorEvent
import es.marcrdz.presentation.mappers.toPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MainEventHandler : PresentationContract.EventFlowHandler<UserEvent<MainEvent.UI>, ViewState<MainEvent.Data>>

class MainEventHandlerImpl @Inject constructor(
    @FetchCharactersUseCase private val fetchCharactersUseCase: UseCase<@JvmSuppressWildcards Nothing, @JvmSuppressWildcards ResultDO<CharacterDO>>
) : MainEventHandler {

    override suspend fun handleInit(): Flow<ViewState<MainEvent.Data>> = loadCharacters()

    override suspend fun handleClear(): Flow<ViewState<MainEvent.Data>> = flow { }

    override suspend fun handleEvent(viewEvent: UserEvent<MainEvent.UI>): Flow<ViewState<MainEvent.Data>> =
        when (viewEvent.event) {
            MainEvent.UI. LoadCharacters -> loadCharacters()
            is MainEvent.UI.CharacterSelected -> flow { }
        }

    private fun loadCharacters() = flow {
        emit(BackgroundState(BackgroundEvent.Loading))
        fetchCharactersUseCase().let { result ->
            emit(BackgroundState(BackgroundEvent.Idle))
            result.fold({ emit(FailState(it.toErrorEvent())) }) { chars ->
                emit(StateChange(MainEvent.Data.CharactersFetched(chars.toPresentation())))
            }
        }
    }
}
