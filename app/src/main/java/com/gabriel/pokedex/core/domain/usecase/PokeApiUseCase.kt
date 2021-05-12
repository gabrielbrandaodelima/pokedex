package com.gabriel.pokedex.core.domain.usecase

import com.gabriel.pokedex.core.data.repository.PokeApiRepository
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import com.gabriel.pokedex.core.domain.model.response.PokemonListingResponse
import kotlinx.coroutines.flow.Flow


class PokeApiUseCase (
    private val repository: PokeApiRepository
) {

    suspend fun executeFetchPokemonsList(offset: Int): Flow<PokemonListingResponse?> {
        return repository.fetchPokemonsList(offset)
    }

    suspend fun executeGetPokemonDetail(id: String): Flow<Pokemon?> {
        return repository.getPokemonDetail(id)
    }
    suspend fun executePostPokemon(pokemon: Pokemon): Flow<Boolean?> {
        return repository.postPokemon(pokemon)
    }

}