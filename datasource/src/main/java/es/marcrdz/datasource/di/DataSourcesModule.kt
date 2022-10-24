package es.marcrdz.datasource.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.marcrdz.data.DataContract
import es.marcrdz.datasource.sources.CharacterDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {

    @Binds
    abstract fun bindStocksRemoteDataSource(datasource: CharacterDataSource): DataContract.CharacterDataSource.Remote

    @Binds
    abstract fun bindStocksCacheDataSource(datasource: CharacterDataSource): DataContract.CharacterDataSource.Cache

}
