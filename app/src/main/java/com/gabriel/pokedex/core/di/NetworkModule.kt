package com.gabriel.pokedex.core.di

import com.gabriel.pokedex.core.data.remote.PokeApiRemote
import com.gabriel.pokedex.core.data.repository.PokeApiRepository
import com.gabriel.pokedex.core.data.repository.PokeApiRepositoryImp
import com.gabriel.pokedex.core.domain.usecase.PokeApiUseCase
import org.koin.dsl.module

val networkModule = module {

    single { PokeApiRemote() }
    single { PokeApiUseCase(get()) }
    single<PokeApiRepository> { PokeApiRepositoryImp(get()) }


}
