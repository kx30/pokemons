package ru.webant.pokemons.ui.card

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.webant.core.gateways.CardsGateway
import ru.webant.core.gateways.FavoriteCardsGateway
import ru.webant.core.models.CardEntity
import ru.webant.pokemons.App
import javax.inject.Inject

@InjectViewState
class CardPresenter : MvpPresenter<CardView>() {

    private val downloadCards = ArrayList<CardEntity>()
    private var displayCards = ArrayList<CardEntity>()
    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 1
    @Inject
    lateinit var cardsGatewayImpl: CardsGateway
    @Inject
    lateinit var favoriteCardsGatewayImpl: FavoriteCardsGateway


    init {
        App.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadCards(true)
    }

    fun loadCards(firstLoad: Boolean) {
        cardsGatewayImpl.getCards(page = currentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                cardsGatewayImpl.getCardsFromDatabase(currentPage).forEach { card ->
                    downloadCards.add(card)
                }
            }
            .doFinally {
                if (firstLoad) {
                    viewState.initRecyclerView(downloadCards)
                }
                currentPage++
                viewState.notifyDataSetChangedAdapter(downloadCards)
                viewState.changeStateAfterDownload()
            }
            .subscribe({ result ->
                result.cards.forEach { card ->
                    downloadCards.add(card)
                    cardsGatewayImpl.saveCard(card)
                }
            }, {
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    fun updateSearchCards(newText: String) {
        displayCards.clear()
        cardsGatewayImpl.searchCards(newText)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                result.cards.forEach { foundCard ->
                    displayCards.add(foundCard)
                }
                if (displayCards.isEmpty()) {
                    viewState.changeSearchIsEmptyTextView(true)
                } else {
                    viewState.changeSearchIsEmptyTextView(false)
                }
                viewState.notifyDataSetChangedAdapter(displayCards)
            }, {
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    fun clearSearchCards() {
        displayCards.clear()
        displayCards.addAll(downloadCards)
        viewState.notifyDataSetChangedAdapter(displayCards)
    }

    fun changeStateBeforeDownload() {
        viewState.changeStateBeforeDownload()
    }

    fun replaceByDetailInformationFragment(card: CardEntity) {
        viewState.replaceByDetailInformationFragment(card)
    }

    fun updateFavoriteCard(card: CardEntity) {
        favoriteCardsGatewayImpl.updateFavoriteCard(card)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}