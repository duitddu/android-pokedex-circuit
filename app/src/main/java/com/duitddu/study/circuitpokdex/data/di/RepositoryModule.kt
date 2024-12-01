package com.duitddu.study.circuitpokdex.data.di

import com.duitddu.study.circuitpokdex.data.repository.PokemonRepositoryImpl
import com.duitddu.study.circuitpokdex.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPokemonRepository(
        impl: PokemonRepositoryImpl
    ): PokemonRepository
}