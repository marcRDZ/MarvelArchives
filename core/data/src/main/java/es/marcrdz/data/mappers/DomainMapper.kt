package es.marcrdz.data.mappers

import arrow.core.Either
import es.marcrdz.data.domain.*
import es.marcrdz.domain.domain.*
import java.util.*

fun ErrorDTO.toDomainError(): DomainError = when (this) {
    ErrorDTO.Network -> DomainError.Network
    ErrorDTO.NoData -> DomainError.NoData
    ErrorDTO.Unknown -> DomainError.Unknown
    ErrorDTO.Forbidden -> DomainError.Forbidden
    ErrorDTO.Unauthorized -> DomainError.Unauthorized
    is ErrorDTO.Exception -> DomainError.Exception(code, msg)
}

fun CharacterDTO.toDomain() = CharacterDO(
    id = id ?: 0,
    name = name.orEmpty(),
    description = description.orEmpty(),
    modified = modified ?: Date(),
    resourceURI = resourceURI.orEmpty(),
    urls = urls.map { it.toDomain() },
    thumbnail = thumbnail.toDomain(),
)

fun ImageDTO?.toDomain() = ImageDO(
    path = this?.path.orEmpty(),
    extension = this?.extension.orEmpty()
)

fun UrlDTO.toDomain() = when (type) {
    "detail" -> UrlDO.Detail(url.orEmpty())
    "wiki" -> UrlDO.Wiki(url.orEmpty())
    "comiclink" -> UrlDO.ComicLink(url.orEmpty())
    else -> UrlDO.Other(url.orEmpty())
}

fun <T, S> Either<ErrorDTO, ResultDTO<T>>.toDomain(map: (T) -> S) =
    bimap({ it.toDomainError() }) { result ->
        result.toDomain { map(it) }
    }

fun <T, S> ResultDTO<T>.toDomain(map: (T) -> S) = ResultDO<S>(
    copyright = copyright.orEmpty(),
    attributionText = attributionText.orEmpty(),
    attributionHTML = attributionHTML.orEmpty(),
    etag = etag.orEmpty(),
    data = data.toDomain { map(it) }
)

fun <T, S> PageDTO<T>?.toDomain(map: (T) -> S) = PageDO<S>(
    offset = this?.offset ?: 0,
    limit = this?.limit ?: 0,
    total = this?.total ?: 0,
    count = this?.count ?: 0,
    results = this?.results?.map { map(it) } ?: emptyList()
)

