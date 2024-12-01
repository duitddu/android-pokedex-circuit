package com.duitddu.study.circuitpokdex.domain.entity

import kotlinx.collections.immutable.ImmutableList

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val weight: Int,
    val height: Int,
    val stats: ImmutableList<PokemonStat>,
    val types: ImmutableList<PokemonType>
)

data class PokemonStat(
    val name: String,
    val stat: Int
)

enum class PokemonType(
    val value: String
) {
    NORMAL("normal"),
    FIRE("fire"),
    WATER("water"),
    ELECTRIC("electric"),
    GRASS("grass"),
    ICE("ice"),
    FIGHTING("fighting"),
    POISON("poison"),
    GROUND("ground"),
    FLYING("flying"),
    PSYCHIC("psychic"),
    BUG("bug"),
    ROCK("rock"),
    GHOST("ghost"),
    DRAGON("dragon"),
    DARK("dark"),
    STEEL("steel"),
    FAIRY("fairy")
}
