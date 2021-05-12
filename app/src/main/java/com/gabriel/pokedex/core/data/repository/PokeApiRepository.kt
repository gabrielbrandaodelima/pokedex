package com.gabriel.pokedex.core.data.repository

import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListingResponse
import kotlinx.coroutines.flow.Flow


interface PokeApiRepository {
    suspend fun fetchPokemonsList(offset: Int): Flow<PokemonListingResponse?>
    suspend fun getPokemonDetail(id: String): Flow<Pokemon?>
    suspend fun postPokemon(pokemon: Pokemon): Flow<Void?>
}

