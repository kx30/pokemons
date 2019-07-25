package ru.webant.pokemons.ui.favorite_card

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.webant.core.gateways.FavoriteCardsGateway
import ru.webant.core.models.CardEntity
import ru.webant.pokemons.App
import javax.inject.Inject

@InjectViewState
class FavoriteCardPresenter : MvpPresenter<FavoriteCardView>() {

    private var currentPage = 0
    private val firstPage = 0
    private val favoriteCards = ArrayList<CardEntity>()
    @Inject
    lateinit var favoriteCardsGatewayImpl: FavoriteCardsGateway


    init {
        App.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadFavoriteCards(true)
        checkIfThereIsNotFavorites()
    }

    fun loadFavoriteCards(firstLoad: Boolean) {
        if (favoriteCardsGatewayImpl.getFavoriteCards(currentPage).isNotEmpty()) {
            viewState.changeStateBeforeDownload()
            favoriteCardsGatewayImpl.getFavoriteCards(currentPage).forEach { favoriteCard ->
                favoriteCards.add(favoriteCard)
            }
            if (firstLoad) {
                viewState.initRecyclerView(favoriteCards)
            }
            currentPage++
            viewState.notifyDataSetChangedAdapter(favoriteCards)
            viewState.changeStateAfterDownload()
        }
    }

    private fun checkIfThereIsNotFavorites() {
        if (favoriteCardsGatewayImpl.getFavoriteCards(firstPage).isEmpty()) {
            viewState.showThereIsNotFavoriteTextView()
            viewState.changeStateAfterDownload()
        }
    }

    fun replaceByDetailInformationFragment(card: CardEntity) {
        viewState.replaceByDetailInformationFragment(card)
    }

    fun updateFavoriteCard(card: CardEntity) {
        favoriteCardsGatewayImpl.updateFavoriteCard(card)
    }
}