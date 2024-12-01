package com.duitddu.study.circuitpokdex.domain.repository

import com.duitddu.study.circuitpokdex.domain.entity.Pokemon
import com.duitddu.study.circuitpokdex.domain.entity.PokemonDetail

interface PokemonRepository {
    suspend fun getPokemons(offset: Int, limit: Int): List<Pokemon>
    suspend fun getPokemonDetail(name: String): PokemonDetail
}
