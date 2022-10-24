package es.marcrdz.datasource.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.marcrdz.datasource.client.ClientConfig
import es.marcrdz.datasource.services.CharactersService
import es.marcrdz.datasource.services.CharactersServiceImpl

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {

    @Provides
    fun provideCharactersService(): CharactersService = CharactersServiceImpl(ClientConfig())

}
