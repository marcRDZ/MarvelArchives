package es.marcrdz.data.repositories

import arrow.core.Either
import es.marcrdz.data.DataContract
import es.marcrdz.data.mappers.toDomain
import es.marcrdz.data.mappers.toDomainError
import es.marcrdz.domain.DomainContract
import es.marcrdz.domain.domain.CharacterDO
import es.marcrdz.domain.domain.DomainError
import es.marcrdz.domain.domain.ResultDO
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val cacheDataSource: DataContract.CharacterDataSource.Cache,
    private val remoteDataSource: DataContract.CharacterDataSource.Remote
) : DomainContract.CharactersRepository {

    override suspend fun fetchCharacters(): Either<DomainError, ResultDO<CharacterDO>> {
        val cache = cacheDataSource.getCharacters().orNull()
        val limit = cache?.data?.limit ?: 0
        val offset = cache?.data?.offset ?: 0
        return remoteDataSource.fetchCharacters(offset = limit + offset)
            .bimap({ it.toDomainError() }) { result ->
                val updatedCache = result.copy(
                    data = result.data?.copy(
                        results = listOfNotNull(
                            cache?.data?.results,
                            result.data.results
                        ).flatten()
                    )
                )
                cacheDataSource.cacheCharactersResult(updatedCache)
                updatedCache.toDomain { it.toDomain() }
            }
    }
}
