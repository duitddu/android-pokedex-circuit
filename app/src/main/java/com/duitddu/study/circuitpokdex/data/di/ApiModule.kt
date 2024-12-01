package com.duitddu.study.circuitpokdex.data.di

import com.duitddu.study.circuitpokdex.data.source.remote.api.PokemonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providePokemonApi(
        @HttpClientModule.PokemonHttpClient httpClient: HttpClient
    ): PokemonApi = PokemonApi(client = httpClient)
}
