package es.marcrdz.data.domain

import java.util.*

sealed class ErrorDTO {
    object Unknown : ErrorDTO()
    object Network : ErrorDTO()
    object Unauthorized : ErrorDTO()
    object Forbidden : ErrorDTO()
    object NoData : ErrorDTO()
    data class Exception(val code: String, val msg: String) : ErrorDTO()
}

data class ResultDTO<T>(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val etag: String?,
    val data: PageDTO<T>?
)

data class PageDTO<T>(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<T>?
)

data class CharacterDTO(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: Date?,
    val resourceURI: String?,
    val urls: List<UrlDTO>,
    val thumbnail: ImageDTO?,
    val comics: SummaryListDTO<ComicSummaryDTO>?,
    val stories: SummaryListDTO<StorySummaryDTO>?,
    val events: SummaryListDTO<EventSummaryDTO>?,
    val series: SummaryListDTO<SeriesSummaryDTO>?,
)

data class ImageDTO(
    val path: String?,
    val extension: String?
)

data class SummaryListDTO<T : SummaryDTO>(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<T>?
)

class ComicSummaryDTO(
    resourceURI: String?,
    name: String?,
) : SummaryDTO(resourceURI, name)


class StorySummaryDTO(
    resourceURI: String?,
    name: String?,
    val type: String?
) : SummaryDTO(resourceURI, name)

class EventSummaryDTO(
    resourceURI: String?,
    name: String?,
) : SummaryDTO(resourceURI, name)

class SeriesSummaryDTO(
    resourceURI: String?,
    name: String?,
) : SummaryDTO(resourceURI, name)

open class SummaryDTO(
    val resourceURI: String?,
    val name: String?
)

data class UrlDTO(
    val type: String?,
    val url: String?
)