package es.marcrdz.domain.usecases

import arrow.core.Either
import es.marcrdz.domain.DomainContract
import es.marcrdz.domain.domain.CharacterDO
import es.marcrdz.domain.domain.DomainError
import es.marcrdz.domain.domain.ResultDO
import javax.inject.Inject
import javax.inject.Qualifier

@Qualifier
annotation class FetchCharactersUseCase

class FetchCharactersUc @Inject constructor(
    private val repository: DomainContract.CharactersRepository
) : UseCase<@JvmSuppressWildcards Any, @JvmSuppressWildcards ResultDO<CharacterDO>> {

    override suspend fun invoke(param: Any?): Either<DomainError, ResultDO<CharacterDO>> =
        repository.fetchCharacters()

}
