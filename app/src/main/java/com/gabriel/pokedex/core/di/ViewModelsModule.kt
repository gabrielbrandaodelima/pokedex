package com.gabriel.pokedex.core.di

import com.gabriel.pokedex.features.viewmodel.PokedexViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { PokedexViewModel(get()) }
}
