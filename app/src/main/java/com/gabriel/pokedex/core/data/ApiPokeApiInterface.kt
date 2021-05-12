package com.gabriel.pokedex.core.data

import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListingResponse
import retrofit2.http.*


interface ApiPokeApiInterface {

    @GET("api/v2/pokemon/")
    suspend fun fetchPokemonsList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20,
    ): PokemonListingResponse

    @GET("api/v2/pokemon/{id}/")
    suspend fun getPokemonDetail(
        @Path("id") id: String
    ): Pokemon


}