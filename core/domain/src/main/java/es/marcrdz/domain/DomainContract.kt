package es.marcrdz.domain

import arrow.core.Either
import es.marcrdz.domain.domain.CharacterDO
import es.marcrdz.domain.domain.DomainError
import es.marcrdz.domain.domain.ResultDO

interface DomainContract {

    interface CharactersRepository {
        suspend fun fetchCharacters(): Either<DomainError, ResultDO<CharacterDO>>
    }
}