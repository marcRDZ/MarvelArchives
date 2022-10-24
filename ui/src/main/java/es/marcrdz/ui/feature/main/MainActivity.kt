package es.marcrdz.ui.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import es.marcrdz.presentation.handlers.main.MainEvent
import es.marcrdz.ui.base.BaseView
import es.marcrdz.ui.composables.CharacterList
import es.marcrdz.ui.composables.main.MainContent
import es.marcrdz.ui.theme.MarvelArchivesTheme

@AndroidEntryPoint
class MainActivity : BaseView<MainEvent.UI, MainEvent.Data, MainStateHolder, MainViewModel>,
    ComponentActivity() {

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelArchivesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent(
                        stateHolder = viewModel.stateHolder,
                        onRetry = { viewModel.processUserEvent(it) }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MarvelArchivesTheme {
        CharacterList(
            characters = emptyList(),
            onEndReached = { }
        )
    }
}