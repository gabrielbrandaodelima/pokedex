package com.gabriel.pokedex.core.platform

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment(@LayoutRes layoutIdRes: Int = 0) : Fragment(layoutIdRes) {


    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    protected fun handleFailure(failure: String?) {

        Toast.makeText(requireContext(), failure, Toast.LENGTH_SHORT).show()
    }
}
