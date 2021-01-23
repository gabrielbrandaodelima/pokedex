package com.gabriel.pokedex.core.extensions

import android.view.View


fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.INVISIBLE }

fun View.gone() { this.visibility = View.GONE }

fun View.visibility(boolean: Boolean) = if(boolean) this.visible() else this.gone()
