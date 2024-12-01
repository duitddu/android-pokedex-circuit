package com.duitddu.study.circuitpokdex.data.source.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<PokemonStatResponse>,
    val types: List<PokemonTypeResponse>
)

@Serializable
data class PokemonTypeResponse(
    val type: PokemonTypeNameResponse,
)

@Serializable
data class PokemonTypeNameResponse(
    val name: String
)

@Serializable
data class PokemonStatResponse(
    @SerialName("base_stat")
    val baseStat: Int,
    val stat: PokemonStatNameResponse,
)

@Serializable
data class PokemonStatNameResponse(
    val name: String
)
