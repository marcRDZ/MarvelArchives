package es.marcrdz.data.repositories

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import es.marcrdz.data.DataContract
import es.marcrdz.data.domain.CharacterDTO
import es.marcrdz.data.domain.ErrorDTO
import es.marcrdz.data.domain.ResultDTO
import es.marcrdz.domain.DomainContract
import es.marcrdz.domain.domain.CharacterDO
import es.marcrdz.domain.domain.DomainError
import es.marcrdz.domain.domain.ResultDO
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersRepositoryTest {

    @MockK
    private lateinit var remoteDataSource: DataContract.CharacterDataSource.Remote

    @MockK
    private lateinit var cacheDataSource: DataContract.CharacterDataSource.Cache

    private val charactersRepository: DomainContract.CharactersRepository by lazy {
        CharacterRepository(cacheDataSource, remoteDataSource)
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When 'fetchCharacters' is unsuccessful, failure is returned`() = runBlockingTest {
        //given
        coEvery { cacheDataSource.getCharacters() } returns ErrorDTO.NoData.left()
        coEvery { remoteDataSource.fetchCharacters(any()) } returns ErrorDTO.Unknown.left()
        //when
        val result = charactersRepository.fetchCharacters()
        //then
        assertTrue((result as? Either.Left<DomainError>)?.a is DomainError.Unknown)
        coVerify { remoteDataSource.fetchCharacters(any()) }
    }

    @Test
    fun `When 'fetchCharacters' is successful and no result is cached, data is returned`() = runBlockingTest {
        //given
        coEvery { cacheDataSource.getCharacters() } returns ErrorDTO.NoData.left()
        coEvery {
            remoteDataSource.fetchCharacters(any())
        } returns mockk<ResultDTO<CharacterDTO>>(relaxed = true).right()
        coEvery { cacheDataSource.cacheCharactersResult(any()) } returns Unit
        //when
        val result = charactersRepository.fetchCharacters()
        //then
        assertTrue(result is Either.Right<ResultDO<CharacterDO>>)
        coVerifySequence {
            cacheDataSource.getCharacters()
            remoteDataSource.fetchCharacters(any())
            cacheDataSource.cacheCharactersResult(any())
        }
    }

    @Test
    fun `When 'fetchCharacters' is successful and there is result cached, data is returned`() = runBlockingTest {
        //given
        coEvery {
            cacheDataSource.getCharacters()
        } returns mockk<ResultDTO<CharacterDTO>>(relaxed = true).right()
        coEvery {
            remoteDataSource.fetchCharacters(any())
        } returns mockk<ResultDTO<CharacterDTO>>(relaxed = true).right()
        coEvery { cacheDataSource.cacheCharactersResult(any()) } returns Unit
        //when
        val result = charactersRepository.fetchCharacters()
        //then
        assertTrue(result is Either.Right<ResultDO<CharacterDO>>)
        coVerifySequence {
            cacheDataSource.getCharacters()
            remoteDataSource.fetchCharacters(any())
            cacheDataSource.cacheCharactersResult(any())
        }
    }

}
