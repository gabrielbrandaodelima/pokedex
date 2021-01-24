package com.gabriel.pokedex.core.data.repository

import com.gabriel.pokedex.core.data.remote.PokeApiRemote
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListing
import kotlinx.coroutines.flow.Flow


interface PokeApiRepository {
    suspend fun fetchPokemonsList(offset: Int): Flow<List<PokemonListing>?>
    suspend fun getPokemonDetail(id: String): Flow<Pokemon?>
}

class PokeApiRepositoryImp (
    private val remote: PokeApiRemote
) : PokeApiRepository {

    override suspend fun fetchPokemonsList(offset: Int): Flow<List<PokemonListing>?> {
        return remote.fetchPokemonsList(offset)
    }
    override suspend fun getPokemonDetail(id: String): Flow<Pokemon?> {
        return remote.getPokemonDetail(id)
    }

}