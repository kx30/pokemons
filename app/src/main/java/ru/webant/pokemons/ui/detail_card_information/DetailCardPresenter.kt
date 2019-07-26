package ru.webant.pokemons.ui.detail_card_information

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.webant.core.models.CardEntity

@InjectViewState
class DetailCardPresenter : MvpPresenter<DetailCardView>() {

    fun showDetailInformation(card: CardEntity) {
        viewState.initDetailInformation(card)
        card.attacks.let {
            viewState.initRecyclerView(it)
        }
    }
}