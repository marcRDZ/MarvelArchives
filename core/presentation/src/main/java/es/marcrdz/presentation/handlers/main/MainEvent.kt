package es.marcrdz.presentation.handlers.main

import es.marcrdz.presentation.base.Event
import es.marcrdz.presentation.domain.CharacterVO
import es.marcrdz.presentation.domain.CharactersVO

sealed class MainEvent {

    sealed class UI : Event {
        object LoadCharacters : UI()
        class CharacterSelected(val id: Int) : UI()
    }

    sealed class Data: Event {
        class CharactersFetched(val characters: CharactersVO) : Data()
        class CharacterDetailFetched(val character: CharacterVO) : Data()
    }

}