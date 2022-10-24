package es.marcrdz.presentation.mappers

import es.marcrdz.domain.domain.CharacterDO
import es.marcrdz.domain.domain.DomainError
import es.marcrdz.domain.domain.ResultDO
import es.marcrdz.presentation.base.ErrorEvent
import es.marcrdz.presentation.domain.CharacterVO
import es.marcrdz.presentation.domain.CharactersVO

fun DomainError.toErrorEvent(): ErrorEvent = when (this) {
    is DomainError.Exception -> ErrorEvent.Exception(type = code, msg = msg)
    DomainError.Network -> ErrorEvent.Network()
    DomainError.NoData -> ErrorEvent.NoData()
    DomainError.Unknown -> ErrorEvent.Unknown()
    DomainError.Forbidden -> ErrorEvent.Forbidden()
    DomainError.Unauthorized -> ErrorEvent.Unauthorized()
}

fun ResultDO<CharacterDO>.toPresentation() = CharactersVO(
    copyright = attributionHTML,
    content = data.results.map { it.toPresentation() }
)

fun CharacterDO.toPresentation() = CharacterVO(
    name = name,
    description = description,
    thumbnail = "${thumbnail.path}.${thumbnail.extension}"
)
