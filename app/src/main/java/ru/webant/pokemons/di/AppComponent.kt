package ru.webant.pokemons.di

import dagger.Component
import ru.webant.pokemons.ui.adapters.CardAdapter
import ru.webant.pokemons.ui.card.CardPresenter
import ru.webant.pokemons.ui.favorite_card.FavoriteCardPresenter
import ru.webant.pokemons.ui.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [GatewayModule::class])
interface AppComponent {

    fun inject(target: CardPresenter)
    fun inject(target: FavoriteCardPresenter)
    fun inject(target: CardAdapter)
}