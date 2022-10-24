package es.marcrdz.ui.feature.main

import es.marcrdz.presentation.domain.CharacterVO
import es.marcrdz.presentation.domain.CharactersVO
import es.marcrdz.presentation.handlers.main.MainEvent
import es.marcrdz.ui.base.BaseStateHolder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainStateHolder: BaseStateHolder<MainEvent.Data>() {
    private val _characters: MutableSharedFlow<CharactersVO> by lazy { MutableSharedFlow() }
    val characters: SharedFlow<CharactersVO>
        get() = _characters.asSharedFlow()
    private val _character: MutableSharedFlow<CharacterVO> by lazy { MutableSharedFlow() }
    val character: SharedFlow<CharacterVO>
        get() = _character.asSharedFlow()

    override suspend fun emitStateChange(event: MainEvent.Data) {
        when(event) {
            is MainEvent.Data.CharactersFetched -> _characters.emit(event.characters)
            is MainEvent.Data.CharacterDetailFetched -> _character.emit(event.character)
        }
    }

}
