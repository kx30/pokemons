package ru.webant.pokemons.ui.detail_card_information

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_card.*
import ru.webant.core.models.CardEntity
import ru.webant.core.models.TypeOfAttacksEntity
import ru.webant.pokemons.R
import ru.webant.pokemons.ui.adapters.TypeOfAttackAdapter
import ru.webant.pokemons.ui.main.MainActivity

class DetailCardFragment : MvpAppCompatFragment(), DetailCardView {


    private val TAG = "DetailCardFragment"
    @InjectPresenter
    lateinit var detailCardPresenter: DetailCardPresenter
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val card = arguments?.getSerializable("pokemon") as CardEntity
        detailCardPresenter.showDetailInformation(card)
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                (activity as MainActivity).mainPresenter.showBottomNavigationMenu()
                fragmentManager?.popBackStack()
                return@setOnKeyListener true
            }
            false
        }
    }

    override fun initDetailInformation(card: CardEntity) {
        Picasso.get()
            .load(card.imageUrlHiRes)
            .into(detailCardImageView)
        pokemonNameTextView.text = card.name
        pokemonRarityTextView.text = card.rarity
        pokemonTypeTextView.text = card.supertype
        pokemonSubtypeTextView.text = card.subtype
        pokemonHPTextView.text = card.hp
    }

    override fun initRecyclerView(typeOfAttacks: List<TypeOfAttacksEntity>) {
        typeOfAttackRecyclerView.layoutManager = LinearLayoutManager(context)
        typeOfAttackRecyclerView.adapter = TypeOfAttackAdapter(typeOfAttacks)
    }
}