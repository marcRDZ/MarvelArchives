package es.marcrdz.presentation.handlers.main

import es.marcrdz.domain.domain.CharacterDO
import es.marcrdz.domain.domain.ResultDO
import es.marcrdz.domain.usecases.UseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainEventHandlerImplTest {

    @MockK
    lateinit var fetchCharactersUc: UseCase<Nothing, ResultDO<CharacterDO>>

    private val eventHandler: MainEventHandler by lazy {
        MainEventHandlerImpl(fetchCharactersUc)
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When 'handleOnInit' executes and 'fetchCharactersUc' fails, error event is returned`() =
        runBlockingTest {

        }
}