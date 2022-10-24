package es.marcrdz.ui.composables.main

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import es.marcrdz.presentation.base.BackgroundEvent
import es.marcrdz.presentation.base.UserEvent
import es.marcrdz.presentation.handlers.main.MainEvent
import es.marcrdz.ui.R
import es.marcrdz.ui.composables.CharacterList
import es.marcrdz.ui.composables.SimpleLoadingDialog
import es.marcrdz.ui.feature.main.MainStateHolder
import es.marcrdz.ui.theme.MarvelGray


@Composable
fun MainContent(
    stateHolder: MainStateHolder,
    onRetry: (UserEvent<MainEvent.UI>) -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val backgroundState by stateHolder.backgroundState.collectAsState()
    val failState by stateHolder.failState.collectAsState(initial = null)
    val charactersState by stateHolder.characters.collectAsState(initial = null)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            modifier = modifier.padding(all = 4.dp)
                        )
                    }

                }
            )
        },
        bottomBar = {
            charactersState?.let { state ->
                AndroidView(
                    modifier = modifier
                        .padding(all = 8.dp)
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    factory = { context -> TextView(context) },
                    update = {
                        it.text =
                            HtmlCompat.fromHtml(
                                state.copyright,
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            )
                        it.movementMethod = LinkMovementMethod.getInstance()
                    }
                )
            }
        },
        backgroundColor = MarvelGray
    ) { paddingValues ->
        charactersState?.let {
            CharacterList(
                characters = it.content,
                onEndReached = { onRetry(UserEvent(MainEvent.UI.LoadCharacters)) },
                modifier = modifier.padding(paddingValues)
            )
        }
        failState?.let {
            LaunchedEffect(failState) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Something went wrong!",
                    actionLabel = "Retry"
                ).takeIf { it == SnackbarResult.ActionPerformed }?.let {
                    onRetry(UserEvent(MainEvent.UI.LoadCharacters))
                }
            }
        }
        if (backgroundState is BackgroundEvent.Loading) SimpleLoadingDialog(modifier)
    }
}