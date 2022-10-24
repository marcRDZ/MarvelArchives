package es.marcrdz.datasource.services

import arrow.core.Either
import es.marcrdz.data.domain.CharacterDTO
import es.marcrdz.data.domain.ErrorDTO
import es.marcrdz.data.domain.ResultDTO
import es.marcrdz.datasource.client.ClientConfig
import es.marcrdz.datasource.client.EitherCallAdapterFactory
import es.marcrdz.datasource.client.MarvelApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(@Query("offset") offset: Int): Either<ErrorDTO, ResultDTO<CharacterDTO>>

}

internal class CharactersServiceImpl(config: ClientConfig) : CharactersService by Retrofit.Builder()
    .baseUrl(config.rootUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(EitherCallAdapterFactory())
    .client(
        OkHttpClient.Builder()
            .apply { config.okHttpConfig }
            .addInterceptor(MarvelApiKeyInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            ).build()
    )
    .build()
    .create(CharactersService::class.java)