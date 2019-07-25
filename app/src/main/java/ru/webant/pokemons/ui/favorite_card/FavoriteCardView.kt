package ru.webant.pokemons.ui.favorite_card

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.core.models.CardEntity

interface FavoriteCardView: MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun replaceByDetailInformationFragment(card: CardEntity)

    fun initRecyclerView(cards: List<CardEntity>)
    fun notifyDataSetChangedAdapter(cards: List<CardEntity>)
    fun changeStateBeforeDownload()
    fun changeStateAfterDownload()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideThereIsNotFavoriteTextView()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showThereIsNotFavoriteTextView()
}