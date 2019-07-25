package ru.webant.pokemons.ui.favorite_card

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_favorite_card.*
import ru.webant.core.models.CardEntity
import ru.webant.pokemons.R
import ru.webant.pokemons.ui.adapters.AdapterCardInterface
import ru.webant.pokemons.ui.adapters.CardAdapter
import ru.webant.pokemons.ui.detail_card_information.DetailCardFragment
import ru.webant.pokemons.ui.main.MainActivity

class FavoriteCardFragment : MvpAppCompatFragment(), FavoriteCardView, AdapterCardInterface {

    private var currentVisiblePosition: Int? = 0
    private var isLoading = false
    private lateinit var adapter: CardAdapter
    @InjectPresenter
    lateinit var favoriteCardPresenter: FavoriteCardPresenter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnScrollListener()
    }

    override fun showThereIsNotFavoriteTextView() {
        thereIsNotFavorite.visibility = View.VISIBLE
    }

    override fun hideThereIsNotFavoriteTextView() {
        thereIsNotFavorite.visibility = View.GONE
    }

    private fun setOnScrollListener() {
        favoriteCardRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = favoriteCardRecyclerView.layoutManager as GridLayoutManager
                if (!isLoading &&
                    gridLayoutManager.itemCount <= gridLayoutManager.findLastVisibleItemPosition() + 2) {
                    favoriteCardPresenter.loadFavoriteCards(false)
                }
            }
        })
    }

    override fun changeStateBeforeDownload() {
        isLoading = true
        favoriteCardProgressBar.visibility = View.VISIBLE
    }

    override fun changeStateAfterDownload() {
        isLoading = false
        favoriteCardProgressBar.visibility = View.GONE
    }

    override fun initRecyclerView(cards: List<CardEntity>) {
        adapter = CardAdapter(cards, this)
        favoriteCardRecyclerView.layoutManager = GridLayoutManager(context, 2)
        favoriteCardRecyclerView.adapter = adapter
    }

    override fun notifyDataSetChangedAdapter(cards: List<CardEntity>) {
        adapter.updateCardList(cards)
    }

    override fun onResume() {
        super.onResume()

        currentVisiblePosition?.let {
            favoriteCardRecyclerView
                .layoutManager?.scrollToPosition(it)
        }
    }

    override fun onPause() {
        super.onPause()
        favoriteCardRecyclerView.layoutManager?.let {
            currentVisiblePosition = (favoriteCardRecyclerView.layoutManager as GridLayoutManager)
                .findFirstVisibleItemPosition()
        }
    }

    override fun replaceByDetailInformationFragmentFromAdapter(card: CardEntity) {
        favoriteCardPresenter.replaceByDetailInformationFragment(card)
    }

    override fun updateFavoriteCardFromAdapter(card: CardEntity) {
        favoriteCardPresenter.updateFavoriteCard(card)
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
