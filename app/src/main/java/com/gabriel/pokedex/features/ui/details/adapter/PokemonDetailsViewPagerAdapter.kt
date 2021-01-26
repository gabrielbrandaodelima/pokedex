package com.gabriel.pokedex.features.ui.details.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.gabriel.pokedex.R
import com.gabriel.pokedex.core.domain.model.response.Pokemon
import com.gabriel.pokedex.features.ui.about.AboutFragment
import com.gabriel.pokedex.features.ui.moves.MovesFragment
import com.gabriel.pokedex.features.ui.stats.StatsFragment

class PokemonDetailsViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    context: Context,
    private val pokemon: Pokemon?
) : FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    data class Page(val title: String, val ctor: () -> Fragment)

    @Suppress("MoveLambdaOutsideParentheses")
    private val pages = listOf(
        Page(
            context.getString(R.string.title_about),
            { AboutFragment.newInstance(pokemon) }
        ),
        Page(
            context.getString(R.string.title_stats),
            { StatsFragment.newInstance(pokemon) }
        ),
        Page(
            context.getString(R.string.title_moves),
            { MovesFragment.newInstance(pokemon) }
        )
    )

    override fun getItem(position: Int): Fragment {
        return pages[position].ctor()
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pages[position].title
    }
}
