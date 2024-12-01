package com.duitddu.study.circuitpokdex.data.repository

import com.duitddu.study.circuitpokdex.data.repository.mapper.PokemonDetailMapper
import com.duitddu.study.circuitpokdex.data.repository.mapper.PokemonMapper
import com.duitddu.study.circuitpokdex.data.source.remote.api.PokemonApi
import com.duitddu.study.circuitpokdex.domain.entity.Pokemon
import com.duitddu.study.circuitpokdex.domain.entity.PokemonDetail
import com.duitddu.study.circuitpokdex.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonMapper: PokemonMapper,
    private val pokemonDetailMapper: PokemonDetailMapper
): PokemonRepository {

    override suspend fun getPokemons(
        offset: Int,
        limit: Int,
    ): List<Pokemon> = pokemonApi
        .getPokemonList(offset, limit)
        .results
        .map { pokemonMapper.invoke(it) }

    override suspend fun getPokemonDetail(
        name: String
    ): PokemonDetail = pokemonApi
        .getPokemonDetail(name)
        .let { pokemonDetailMapper.invoke(it) }
}
