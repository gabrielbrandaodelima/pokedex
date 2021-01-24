package com.gabriel.pokedex.core.data

import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.core.domain.model.response.PokemonListingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiPokeApiInterface {

    @GET("api/v2/pokemon/")
    suspend fun fetchPokemonsList(
        @Query("offset") offset: Int
    ): PokemonListingResponse

    @GET("api/v2/pokemon/{id}/")
    suspend fun getPokemonDetail(
        @Path("id") id: String
    ): Pokemon

}