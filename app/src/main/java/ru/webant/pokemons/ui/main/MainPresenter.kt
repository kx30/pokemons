package ru.webant.pokemons.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class MainPresenter: MvpPresenter<MainView>() {

    fun showCardFragment() {
        viewState.showCardFragment()
    }

    fun showChosenFragment(fragment: MvpAppCompatFragment) {
        viewState.showChosenFragment(fragment)
    }

    fun hideBottomNavigationMenu() {
        viewState.hideBottomNavigationMenu()
    }

    fun showBottomNavigationMenu() {
        viewState.showBottomNavigationMenu()
    }
}