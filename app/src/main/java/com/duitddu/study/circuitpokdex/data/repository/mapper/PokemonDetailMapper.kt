package com.duitddu.study.circuitpokdex.data.repository.mapper

import com.duitddu.study.circuitpokdex.data.source.remote.response.GetPokemonDetailResponse
import com.duitddu.study.circuitpokdex.data.source.remote.response.PokemonStatResponse
import com.duitddu.study.circuitpokdex.data.source.remote.response.PokemonTypeResponse
import com.duitddu.study.circuitpokdex.domain.entity.PokemonDetail
import com.duitddu.study.circuitpokdex.domain.entity.PokemonStat
import com.duitddu.study.circuitpokdex.domain.entity.PokemonType
import kotlinx.collections.immutable.toImmutableList

class PokemonDetailMapper: Mapper<GetPokemonDetailResponse, PokemonDetail> {
    override fun invoke(
        from: GetPokemonDetailResponse
    ): PokemonDetail = PokemonDetail(
        id = from.id,
        name = from.name,
        imageUrl = POKEMON_IMAGE_BASE_URL + "${from.id}".padStart(3, '0') + ".png",
        weight = from.weight,
        height = from.height,
        stats = from.stats.map { it.toEntity() }.toImmutableList(),
        types = from.types.map { it.toEntity() }.toImmutableList(),
    )

    private fun PokemonStatResponse.toEntity(): PokemonStat = PokemonStat(
        name = stat.name,
        stat = baseStat
    )

    private fun PokemonTypeResponse.toEntity(): PokemonType = PokemonType.entries.find {
        it.value == type.name
    } ?: PokemonType.NORMAL

    companion object {
        private const val POKEMON_IMAGE_BASE_URL = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/"
    }
}
