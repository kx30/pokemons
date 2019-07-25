package ru.webant.pokemons.ui.card

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_card.*
import ru.webant.core.models.CardEntity
import ru.webant.pokemons.R
import ru.webant.pokemons.ui.adapters.AdapterCardInterface
import ru.webant.pokemons.ui.adapters.CardAdapter
import ru.webant.pokemons.ui.detail_card_information.DetailCardFragment
import ru.webant.pokemons.ui.main.MainActivity

class CardFragment : MvpAppCompatFragment(), CardView, AdapterCardInterface {

    private var currentVisiblePosition: Int? = 0
    private var listState: Parcelable? = null
    private var isLoading = false
    private lateinit var adapter: CardAdapter
    @InjectPresenter
    lateinit var cardPresenter: CardPresenter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnScrollListener()
    }

    private fun setOnScrollListener() {
        cardRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = cardRecyclerView.layoutManager as GridLayoutManager
                if (!isLoading &&
                    gridLayoutManager.itemCount <= gridLayoutManager.findLastVisibleItemPosition() + 2
                ) {
                    cardPresenter.changeStateBeforeDownload()
                    cardPresenter.loadCards(false)
                }
            }
        })
    }

    override fun initRecyclerView(cards: List<CardEntity>) {
        adapter = CardAdapter(cards, this)
        cardRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        cardRecyclerView.adapter = adapter
    }

    override fun notifyDataSetChangedAdapter(cards: List<CardEntity>) {
        adapter.updateCardList(cards)
    }

    override fun changeStateBeforeDownload() {
        isLoading = true
        cardProgressBar.visibility = View.VISIBLE
    }

    override fun changeStateAfterDownload() {
        isLoading = false
        cardProgressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        currentVisiblePosition?.let {
            cardRecyclerView.layoutManager?.scrollToPosition(it)
        }

        listState?.let {
            cardRecyclerView.layoutManager?.onRestoreInstanceState(listState)
        }
    }

    override fun onPause() {
        super.onPause()
        cardRecyclerView.layoutManager?.let {
            currentVisiblePosition = (cardRecyclerView.layoutManager as GridLayoutManager)
                .findFirstVisibleItemPosition()
        }
    }

    override fun replaceByDetailInformationFragmentFromAdapter(card: CardEntity) {
        cardPresenter.replaceByDetailInformationFragment(card)
    }

    override fun updateFavoriteCardFromAdapter(card: CardEntity) {
        cardPresenter.updateFavoriteCard(card)
    }

    override fun replaceByDetailInformationFragment(card: CardEntity) {
        val bundle = Bundle()
        bundle.putSerializable("pokemon", card)

        (activity as MainActivity).mainPresenter.hideBottomNavigationMenu()

        val fragment = DetailCardFragment()
        fragment.arguments = bundle

        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}
