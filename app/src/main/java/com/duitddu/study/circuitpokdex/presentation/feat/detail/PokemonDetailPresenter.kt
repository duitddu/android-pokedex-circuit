package com.duitddu.study.circuitpokdex.presentation.feat.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.duitddu.study.circuitpokdex.domain.repository.PokemonRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.components.ActivityRetainedComponent

class PokemonDetailPresenter @AssistedInject constructor(
    @Assisted private val screen: PokemonDetailScreen,
    @Assisted private val navigator: Navigator,
    private val pokemonRepository: PokemonRepository,
) : Presenter<PokemonDetailScreen.State> {

    @Composable
    override fun present(): PokemonDetailScreen.State {
        var state by rememberRetained {
            mutableStateOf<PokemonDetailScreen.State>(
                PokemonDetailScreen.State.Idle(eventSink = ::sinkEvent)
            )
        }

        LaunchedEffect(Unit) {
            val pokemon = pokemonRepository.getPokemonDetail(name = screen.pokemonName)

            state = PokemonDetailScreen.State.Success(
                pokemon = pokemon,
                eventSink = ::sinkEvent
            )
        }

        return state
    }

    private fun sinkEvent(event: PokemonDetailScreen.Event) {
        when (event) {
            is PokemonDetailScreen.Event.OnBack -> {
                navigator.pop()
            }
        }
    }

    @CircuitInject(PokemonDetailScreen::class, ActivityRetainedComponent::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            screen: PokemonDetailScreen,
            navigator: Navigator
        ): PokemonDetailPresenter
    }
}