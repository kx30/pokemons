package ru.webant.pokemons.ui.main

import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCardFragment()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showChosenFragment(fragment: MvpAppCompatFragment)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideBottomNavigationMenu()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showBottomNavigationMenu()
}