package com.gabriel.pokedex.features.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.core.view.isVisible
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.platform.BaseActivity

class MainActivity : BaseActivity(R.layout.activity_main) {
    override fun navHostFragment(): Int = R.id.pokedex_nav_host


    override fun onBackPressed() {
        super.onBackPressed()

    }
}