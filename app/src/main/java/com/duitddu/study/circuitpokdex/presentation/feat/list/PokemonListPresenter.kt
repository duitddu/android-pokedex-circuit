package com.duitddu.study.circuitpokdex.presentation.feat.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.duitddu.study.circuitpokdex.domain.repository.PokemonRepository
import com.duitddu.study.circuitpokdex.presentation.feat.detail.PokemonDetailScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.collections.immutable.toImmutableList

class PokemonListPresenter @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    private val pokemonRepository: PokemonRepository,
) : Presenter<PokemonListScreen.State> {

    @Composable
    override fun present(): PokemonListScreen.State {
        var state by rememberRetained {
            mutableStateOf<PokemonListScreen.State>(PokemonListScreen.State.Idle)
        }

        LaunchedEffect(Unit) {
            // TODO : Need to implement paging
            val pokemons = pokemonRepository.getPokemons(offset = 0, limit = POKEMON_LIMIT)

            state = PokemonListScreen.State.Success(
                pokemons = pokemons.toImmutableList(),
                eventSink = {
                    when (it) {
                        is PokemonListScreen.Event.OnPokemonClicked -> {
                            navigator.goTo(
                                PokemonDetailScreen(pokemonName = it.pokemon.name)
                            )
                        }
                    }
                }
            )
        }

        return state
    }

    @CircuitInject(PokemonListScreen::class, ActivityRetainedComponent::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            navigator: Navigator
        ): PokemonListPresenter
    }

    companion object {
        private const val POKEMON_LIMIT = 151
    }
}