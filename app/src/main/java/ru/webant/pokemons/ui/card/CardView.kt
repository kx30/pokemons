package ru.webant.pokemons.ui.card

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.core.models.CardEntity

interface CardView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun replaceByDetailInformationFragment(card: CardEntity)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun notifyDataSetChangedAdapter(cards: List<CardEntity>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun changeSearchIsEmptyTextView(show: Boolean)

    fun initRecyclerView(cards: List<CardEntity>)
    fun changeStateBeforeDownload()
    fun changeStateAfterDownload()
}