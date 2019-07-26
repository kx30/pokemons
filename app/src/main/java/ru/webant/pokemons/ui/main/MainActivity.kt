package ru.webant.pokemons.ui.main

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import ru.webant.pokemons.R
import ru.webant.pokemons.ui.card.CardFragment
import ru.webant.pokemons.ui.favorite_card.FavoriteCardFragment

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            mainPresenter.showCardFragment()
        }

        setupBottomNavigation()
    }

    override fun showCardFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, CardFragment())
            .commit()
    }

    private fun setupBottomNavigation() {
        bottomNavigationMenu.setTextVisibility(false)
        bottomNavigationMenu.enableAnimation(false)
        for (i in 0 until bottomNavigationMenu.menu.size()) {
            bottomNavigationMenu.setIconTintList(i, null)
        }

        bottomNavigationMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_item_pokemon -> {
                    if (bottomNavigationMenu.currentItem != 0) {
                        mainPresenter.showChosenFragment(CardFragment())
                    }
                    true
                }
                R.id.nav_item_favorite -> {
                    if (bottomNavigationMenu.currentItem != 1) {
                        mainPresenter.showChosenFragment(FavoriteCardFragment())
                    }
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    override fun hideBottomNavigationMenu() {
        bottomNavigationMenu.visibility = View.GONE
    }

    override fun showBottomNavigationMenu() {
        bottomNavigationMenu.visibility = View.VISIBLE
    }

    override fun showChosenFragment(fragment: MvpAppCompatFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
