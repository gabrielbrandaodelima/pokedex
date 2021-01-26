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

    @POST("23328ac8-0cf2-49f7-9bb2-78d8f38bc9a6/")
    suspend fun postPokemon(@Body pokemon: Pokemon): Void?

}