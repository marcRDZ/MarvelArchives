package es.marcrdz.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import es.marcrdz.presentation.domain.CharacterVO
import es.marcrdz.ui.R
import es.marcrdz.ui.theme.MarvelArchivesTheme
import es.marcrdz.ui.theme.MarvelGray
import es.marcrdz.ui.theme.Shapes


@Composable
fun CharacterList(
    characters: List<CharacterVO>,
    onEndReached: () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    val endReached = remember {
        derivedStateOf {
            state.layoutInfo.visibleItemsInfo.lastOrNull()?.index == state.layoutInfo.totalItemsCount -1
        }
    }
    LazyColumn(
        state = state,
        modifier = modifier
            .fillMaxSize()
            .background(color = MarvelGray)
    ) {
        characters.takeIf { it.isNotEmpty() }?.let {
            items(count = it.size) { index ->
                CharacterItem(item = it[index], modifier)
            }
        } ?: run {
            item {
                Text(
                    text = stringResource(id = R.string.nothing_found_here),
                    modifier = modifier.padding(all = 12.dp),
                    color = Color.DarkGray
                )
            }
        }
    }
    if (endReached.value) onEndReached.invoke()
}

@Composable
fun CharacterItem(item: CharacterVO, modifier: Modifier = Modifier) {
    val randomColor: Int = android.graphics.Color.rgb(
        (30..200).random(),
        (30..200).random(),
        (30..200).random()
    )

    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(
                color = Color.White,
                shape = Shapes.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = modifier
                .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 2.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.name,
                color = Color(randomColor)
            )
            item.description.takeIf { it.isNotEmpty() }?.let {
                Text(
                    text = stringResource(id = R.string.description_double_dot),
                    color = Color.LightGray
                )
                Text(
                    text = item.description,
                    color = Color.DarkGray
                )
            }
        }
        Row(
            modifier = modifier
                .padding(start = 12.dp, top = 2.dp, end = 12.dp, bottom = 4.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            GlideImage(
                imageModel = { item.thumbnail },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillWidth
                )
            )
        }
    }

}

@Preview(showBackground = true, widthDp = 300, heightDp = 300)
@Composable
fun DefaultPreview() {
    MarvelArchivesTheme {
        CharacterItem(
            item = CharacterVO(
                name = "3-D Man",
                description = "The unique man in 3D in the world!",
                thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
            )
        )
    }
}