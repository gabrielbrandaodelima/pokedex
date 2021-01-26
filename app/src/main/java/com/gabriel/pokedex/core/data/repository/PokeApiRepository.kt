package com.gabriel.pokedex.core.data.repository

import com.gabriel.pokedex.core.data.remote.PokeApiRemote
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import com.gabriel.pokedex.core.domain.model.response.PokemonListingResponse
import kotlinx.coroutines.flow.Flow


interface PokeApiRepository {
    suspend fun fetchPokemonsList(offset: Int): Flow<PokemonListingResponse?>
    suspend fun getPokemonDetail(id: String): Flow<Pokemon?>
    suspend fun postPokemon(pokemon: Pokemon): Flow<Void?>
}

class PokeApiRepositoryImp (
    private val remote: PokeApiRemote
) : PokeApiRepository {

    override suspend fun fetchPokemonsList(offset: Int): Flow<PokemonListingResponse?> {
        return remote.fetchPokemonsList(offset)
    }
    override suspend fun getPokemonDetail(id: String): Flow<Pokemon?> {
        return remote.getPokemonDetail(id)
    }

    override suspend fun postPokemon(pokemon: Pokemon): Flow<Void?> {
        return remote.postPokemon(pokemon)
    }

}