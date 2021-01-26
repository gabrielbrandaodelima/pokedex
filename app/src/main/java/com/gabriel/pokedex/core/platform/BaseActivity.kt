package com.gabriel.pokedex.core.platform

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.gabriel.pokedex.R

abstract class BaseActivity(@LayoutRes layoutIdRes : Int = 0) : AppCompatActivity(layoutIdRes) {

    private var navController: NavController? = null
    private var navDestination: NavDestination? = null

    @IdRes
    abstract fun navHostFragment(): Int

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setUpNavController()
    }
    private fun setUpNavController() {
        val host: NavHostFragment? = supportFragmentManager
            .findFragmentById(navHostFragment()) as NavHostFragment?

        navController = host?.navController ?: Navigation.findNavController(this, navHostFragment())
        navController?.addOnDestinationChangedListener { _, destination, _ ->
            onDestinationChangedListener(destination)
        }

    }

    open fun onDestinationChangedListener(
        destination: NavDestination,
    ) {
        navDestination = destination

    }
}
