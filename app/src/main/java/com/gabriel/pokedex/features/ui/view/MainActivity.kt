package com.gabriel.pokedex.features.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.platform.BaseActivity

class MainActivity : BaseActivity(R.layout.activity_main) {
    override fun navHostFragment(): Int = R.id.pokedex_nav_host



}