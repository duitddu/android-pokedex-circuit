package com.duitddu.study.circuitpokdex.data.source.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GetPokemonListResponse(
    val results: List<GetPokemonResponse>
)

@Serializable
data class GetPokemonResponse(
    val name: String,
    val url: String
)
