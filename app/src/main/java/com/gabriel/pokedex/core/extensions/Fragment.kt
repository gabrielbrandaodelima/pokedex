package com.gabriel.pokedex.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.*


inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProvider(this, factory)[T::class.java]
    vm.body()
    return vm
}

