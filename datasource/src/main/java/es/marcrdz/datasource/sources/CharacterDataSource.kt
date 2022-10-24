package es.marcrdz.datasource.sources

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import es.marcrdz.data.DataContract
import es.marcrdz.data.domain.CharacterDTO
import es.marcrdz.data.domain.ErrorDTO
import es.marcrdz.data.domain.ResultDTO
import es.marcrdz.datasource.services.CharactersService
import javax.inject.Inject

class CharacterDataSource @Inject constructor(
    private val service: CharactersService
) : DataContract.CharacterDataSource.Cache, DataContract.CharacterDataSource.Remote {

    private var charactersCache: ResultDTO<CharacterDTO>? = null

    override suspend fun getCharacters(): Either<ErrorDTO, ResultDTO<CharacterDTO>> =
        charactersCache?.right() ?: ErrorDTO.NoData.left()

    override suspend fun cacheCharactersResult(result: ResultDTO<CharacterDTO>) {
        charactersCache = result
    }

    override suspend fun fetchCharacters(offset: Int): Either<ErrorDTO, ResultDTO<CharacterDTO>> =
        service.getCharacters(offset)

}
