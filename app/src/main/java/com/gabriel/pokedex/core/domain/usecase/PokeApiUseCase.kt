package com.gabriel.pokedex.core.domain.usecase

import com.gabriel.pokedex.core.data.repository.PokeApiRepository
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import com.gabriel.pokedex.core.domain.model.response.PokemonListingResponse
import kotlinx.coroutines.flow.Flow


class PokeApiUseCase (
    private val repository: PokeApiRepository
) {

    suspend fun fetchPokemonsList(offset: Int): Flow<PokemonListingResponse?> {
        return repository.fetchPokemonsList(offset)
    }

    suspend fun getPokemonDetail(id: String): Flow<Pokemon?> {
        return repository.getPokemonDetail(id)
    }
    suspend fun postPokemon(pokemon: Pokemon): Flow<Void?> {
        return repository.postPokemon(pokemon)
    }

}