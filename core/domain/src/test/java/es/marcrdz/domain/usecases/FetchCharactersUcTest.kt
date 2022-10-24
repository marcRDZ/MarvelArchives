package es.marcrdz.domain.usecases

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import es.marcrdz.domain.DomainContract
import es.marcrdz.domain.domain.CharacterDO
import es.marcrdz.domain.domain.DomainError
import es.marcrdz.domain.domain.ResultDO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchCharactersUcTest {

    @MockK
    private lateinit var charactersRepository: DomainContract.CharactersRepository

    private val fetchCharactersUc: UseCase<Nothing, ResultDO<CharacterDO>> by lazy {
        FetchCharactersUc(charactersRepository)
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When 'fetchCharacters' is unsuccessful, failure is returned`() = runBlockingTest {
        //given
        coEvery { charactersRepository.fetchCharacters() } returns DomainError.Network.left()

        //when
        val response = fetchCharactersUc()

        //then
        assertTrue((response as? Either.Left<DomainError>)?.a is DomainError.Network)
        coVerify { charactersRepository.fetchCharacters() }

    }

    @Test
    fun `When 'fetchCharacters' is successful, data is returned`() = runBlockingTest {
        //given
        coEvery {
            charactersRepository.fetchCharacters()
        } returns mockk<ResultDO<CharacterDO>>(relaxed = true).right()

        //when
        val response = fetchCharactersUc()

        //then
        assertTrue(response is Either.Right<ResultDO<CharacterDO>>)
        coVerify { charactersRepository.fetchCharacters() }

    }

}
