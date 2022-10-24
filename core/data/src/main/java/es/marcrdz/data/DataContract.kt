package es.marcrdz.data

import arrow.core.Either
import es.marcrdz.data.domain.CharacterDTO
import es.marcrdz.data.domain.ErrorDTO
import es.marcrdz.data.domain.ResultDTO

interface DataContract {

    interface CharacterDataSource {

        interface Cache {
            suspend fun getCharacters(): Either<ErrorDTO, ResultDTO<CharacterDTO>>
            suspend fun cacheCharactersResult(result: ResultDTO<CharacterDTO>): Unit
        }

        interface Remote {
            suspend fun fetchCharacters(offset: Int): Either<ErrorDTO, ResultDTO<CharacterDTO>>
        }

    }

}