package es.marcrdz.domain.domain

import java.util.*

sealed class DomainError {
    object Unknown : DomainError()
    object Network : DomainError()
    object Unauthorized : DomainError()
    object Forbidden : DomainError()
    object NoData : DomainError()
    data class Exception(val code: String, val msg: String) : DomainError()
}

data class ResultDO<T>(
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val etag: String,
    val data: PageDO<T>
)

data class PageDO<T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
)

data class CharacterDO(
    val id: Int,
    val name: String,
    val description: String,
    val modified: Date,
    val resourceURI: String,
    val urls: List<UrlDO>,
    val thumbnail: ImageDO,
/*    val comics: SummaryListDO<ComicSummary>,
    val stories: SummaryListDO<StorySummary>,
    val events: SummaryListDO<EventSummary>,
    val series: SummaryListDO<SeriesSummary>*/
)

data class ImageDO(
    val path: String,
    val extension: String
)

data class SummaryListDO<T : SummaryDO>(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<T>
)

data class ComicSummary(
    override val resourceURI: String,
    override val name: String,
) : SummaryDO(resourceURI, name)

data class StorySummary(
    override val resourceURI: String,
    override val name: String,
    val type: String
) : SummaryDO(resourceURI, name)

data class EventSummary(
    override val resourceURI: String,
    override val name: String,
) : SummaryDO(resourceURI, name)

data class SeriesSummary(
    override val resourceURI: String,
    override val name: String,
) : SummaryDO(resourceURI, name)

open class SummaryDO(
    open val resourceURI: String,
    open val name: String
)

sealed class UrlDO(val url: String) {
    class Detail(url: String) : UrlDO(url)
    class Wiki(url: String) : UrlDO(url)
    class ComicLink(url: String) : UrlDO(url)
    class Other(url: String) : UrlDO(url)
}
