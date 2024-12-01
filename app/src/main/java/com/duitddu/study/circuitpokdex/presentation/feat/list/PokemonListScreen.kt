package com.duitddu.study.circuitpokdex.presentation.feat.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.duitddu.study.circuitpokdex.domain.entity.Pokemon
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize

@Parcelize
data object PokemonListScreen: Screen {

    sealed interface State: CircuitUiState {
        data object Idle: State

        data class Success(
            val pokemons: ImmutableList<Pokemon> = emptyList<Pokemon>().toImmutableList(),
            val eventSink: (Event) -> Unit
        ): State
    }

    sealed interface Event: CircuitUiEvent {
        data class OnPokemonClicked(val pokemon: Pokemon): Event
    }
}

@CircuitInject(PokemonListScreen::class, ActivityRetainedComponent::class)
@Composable
fun PokemonList(
    state: PokemonListScreen.State,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar()
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (state) {
                is PokemonListScreen.State.Idle -> {
                    Idle()
                }
                is PokemonListScreen.State.Success -> {
                    Success(
                        pokemons = state.pokemons,
                        onClick = { clicked ->
                            state.eventSink.invoke(PokemonListScreen.Event.OnPokemonClicked(clicked))
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        title = {
            Text("Circuit Pokedex")
        }
    )
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
private fun Success(
    pokemons: ImmutableList<Pokemon>,
    onClick: (Pokemon) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(pokemons) {
            PokemonListItem(
                pokemon = it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun PokemonListItem(
    pokemon: Pokemon,
    onClick: (Pokemon) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick.invoke(pokemon) }
            .background(Color.LightGray)
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp),
    ) {
        Text(
            text = "#${pokemon.id}",
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.TopStart)
        )

        AsyncImage(
            model = pokemon.imageUrl,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f)
                .align(Alignment.Center),
            contentDescription = "PokemonListItem > ${pokemon.name}",
            contentScale = ContentScale.Fit,
        )

        Text(
            text = pokemon.name,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}