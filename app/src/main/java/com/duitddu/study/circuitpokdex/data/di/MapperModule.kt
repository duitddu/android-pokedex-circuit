package com.duitddu.study.circuitpokdex.data.di

import com.duitddu.study.circuitpokdex.data.repository.mapper.PokemonDetailMapper
import com.duitddu.study.circuitpokdex.data.repository.mapper.PokemonMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun providePokemonMapper(): PokemonMapper = PokemonMapper()

    @Provides
    fun providePokemonDetailMapper(): PokemonDetailMapper = PokemonDetailMapper()
}
