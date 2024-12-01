package com.duitddu.study.circuitpokdex.presentation.feat.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.duitddu.study.circuitpokdex.domain.entity.PokemonDetail
import com.duitddu.study.circuitpokdex.domain.entity.PokemonStat
import com.duitddu.study.circuitpokdex.domain.entity.PokemonType
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.amber700
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.blue300
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.blue500
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.blueGray400
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.brown500
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.gray500
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.gray600
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.green500
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.orange500
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.pink400
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.purple400
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.purple500
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.purple600
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.red400
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.red500
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.teal
import com.duitddu.study.circuitpokdex.presentation.core.ui.theme.yellow500
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonDetailScreen(
    val pokemonName: String
): Screen {

    sealed interface State: CircuitUiState {
        val eventSink: (Event) -> Unit

        data class Idle(
            override val eventSink: (Event) -> Unit
        ): State

        data class Success(
            val pokemon: PokemonDetail,
            override val eventSink: (Event) -> Unit
        ): State
    }

    sealed interface Event: CircuitUiEvent {
        data object OnBack: Event
    }
}

@CircuitInject(PokemonDetailScreen::class, ActivityRetainedComponent::class)
@Composable
fun PokemonDetail(
    state: PokemonDetailScreen.State,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onBackPressed = {
                    state.eventSink.invoke(PokemonDetailScreen.Event.OnBack)
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (state) {
                is PokemonDetailScreen.State.Idle -> {
                    Idle()
                }
                is PokemonDetailScreen.State.Success -> {
                    Success(pokemon = state.pokemon)
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
           onClick = onBackPressed
        ) {
            Icon(Icons.AutoMirrored.Default.KeyboardArrowLeft, contentDescription = null)
        }
    }
}

@Composable
private fun Idle() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Success(pokemon: PokemonDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PokemonImage(pokemon.imageUrl)

        Spacer(modifier = Modifier.padding(8.dp))

        PokemonBasicInfo(pokemon.id, pokemon.name)

        Spacer(modifier = Modifier.padding(4.dp))

        PokemonTypes(pokemon.types)

        Spacer(modifier = Modifier.padding(8.dp))

        PokemonPhysical(pokemon.height, pokemon.weight)

        Spacer(modifier = Modifier.padding(16.dp))

        PokemonStats(pokemon.stats)

        Spacer(modifier = Modifier.padding(24.dp))
    }
}

@Composable
private fun PokemonImage(url: String) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth(fraction = 0.7f)
            .aspectRatio(1f)
    )
}

@Composable
private fun PokemonBasicInfo(id: Int, name: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "#$id")
        Text(text = name)
    }
}

@Composable
private fun PokemonTypes(types: List<PokemonType>) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(types) {
            PokemonTypeListItem(it)
        }
    }
}

@Composable
private fun PokemonTypeListItem(
    type: PokemonType
) {
    Text(
        text = type.value,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(type.color)
            .wrapContentSize()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    )
}

@Composable
private fun PokemonPhysical(height: Int, weight: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Height\n$height",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "Weight\n$weight",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
private fun PokemonStats(stats: List<PokemonStat>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        stats.forEach { PokemonStatItem(it) }
    }
}

@Composable
fun PokemonStatItem(
    stat: PokemonStat
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stat.name,
            modifier = Modifier.width(120.dp)
        )
        LinearProgressIndicator(
            progress = { stat.stat / 300f },
            modifier = Modifier
                .height(8.dp)
                .weight(1f)
                .clip(RoundedCornerShape(50)),
            drawStopIndicator = {},
            gapSize = 0.dp
        )
    }
}

private val PokemonType.color: Color
    get() = when (this) {
        PokemonType.NORMAL -> gray500
        PokemonType.FIRE -> red500
        PokemonType.WATER -> blue500
        PokemonType.ELECTRIC -> yellow500
        PokemonType.GRASS -> green500
        PokemonType.ICE -> blue300
        PokemonType.FIGHTING -> red400
        PokemonType.POISON -> purple400
        PokemonType.GROUND -> amber700
        PokemonType.FLYING -> gray600
        PokemonType.PSYCHIC -> purple500
        PokemonType.BUG -> teal
        PokemonType.ROCK -> brown500
        PokemonType.GHOST -> purple600
        PokemonType.DRAGON -> orange500
        PokemonType.DARK -> gray600
        PokemonType.STEEL -> blueGray400
        PokemonType.FAIRY -> pink400
    }
