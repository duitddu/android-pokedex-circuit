package com.duitddu.study.circuitpokdex.data.source.remote.api

import com.duitddu.study.circuitpokdex.data.source.remote.response.GetPokemonDetailResponse
import com.duitddu.study.circuitpokdex.data.source.remote.response.GetPokemonListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class PokemonApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getPokemonList(
        offset: Int,
        limit: Int
    ): GetPokemonListResponse = client.get("pokemon") {
        url {
            parameters.append("offset", offset.toString())
            parameters.append("limit", limit.toString())
        }
    }.body()

    suspend fun getPokemonDetail(
        name: String
    ): GetPokemonDetailResponse = client.get("pokemon/$name").body()
}
