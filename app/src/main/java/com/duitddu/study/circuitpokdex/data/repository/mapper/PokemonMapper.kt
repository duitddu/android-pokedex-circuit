package com.duitddu.study.circuitpokdex.data.repository.mapper

import com.duitddu.study.circuitpokdex.data.source.remote.response.GetPokemonResponse
import com.duitddu.study.circuitpokdex.domain.entity.Pokemon

class PokemonMapper: Mapper<GetPokemonResponse, Pokemon> {
    override fun invoke(
        from: GetPokemonResponse
    ): Pokemon = Pokemon(
        id = from.id,
        name = from.name,
        imageUrl = POKEMON_IMAGE_BASE_URL + "${from.id}".padStart(3, '0') + ".png",
    )

    private val GetPokemonResponse.id: Int
        get() = url.split("/").dropLast(1).last().toInt()

    companion object {
        private const val POKEMON_IMAGE_BASE_URL = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/"
    }
}
