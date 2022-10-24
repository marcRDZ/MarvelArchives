package es.marcrdz.presentation.domain

data class CharactersVO(
    val copyright: String,
    val content: List<CharacterVO>
)

data class CharacterVO(
    val name: String,
    val description: String,
    val thumbnail: String
)