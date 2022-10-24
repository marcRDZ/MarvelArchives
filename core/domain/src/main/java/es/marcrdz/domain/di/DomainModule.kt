package es.marcrdz.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.marcrdz.domain.domain.CharacterDO
import es.marcrdz.domain.domain.ResultDO
import es.marcrdz.domain.usecases.*

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @FetchCharactersUseCase
    @Binds
    abstract fun bindFetchCharactersUc(useCase: FetchCharactersUc): UseCase<Nothing, ResultDO<CharacterDO>>

}
