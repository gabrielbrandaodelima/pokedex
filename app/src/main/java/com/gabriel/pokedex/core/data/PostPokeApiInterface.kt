package com.gabriel.pokedex.core.data

import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListingResponse
import retrofit2.http.*


interface PostPokeApiInterface {

    @POST("cbaf02a3-58ce-4cfe-bb97-d610139d3116/")
    suspend fun postPokemon(@Body pokemon: Pokemon): Void?

}