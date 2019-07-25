package ru.webant.pokemons.ui.detail_card_information

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.core.models.CardEntity
import ru.webant.core.models.TypeOfAttacksEntity

interface DetailCardView: MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun initDetailInformation(card: CardEntity)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun initRecyclerView(typeOfAttacks: List<TypeOfAttacksEntity>)
}