package ru.webant.pokemons.ui.favorite_card

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.core.models.CardEntity

interface FavoriteCardView: MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun replaceByDetailInformationFragment(card: CardEntity)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showThereIsNotFavoriteTextView()

    fun initRecyclerView(cards: List<CardEntity>)
    fun notifyDataSetChangedAdapter(cards: List<CardEntity>)
    fun changeDownloadState(beforeDownload: Boolean)
}