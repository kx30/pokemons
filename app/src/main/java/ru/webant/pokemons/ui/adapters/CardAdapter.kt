package ru.webant.pokemons.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*
import ru.webant.core.gateways.FavoriteCardsGateway
import ru.webant.core.models.CardEntity
import ru.webant.pokemons.App
import ru.webant.pokemons.R
import javax.inject.Inject

class CardAdapter(private var cardList: List<CardEntity>, private val adapterCardView: AdapterCardInterface) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    @Inject
    lateinit var favoriteCardsGatewayImpl: FavoriteCardsGateway


    init {
        App.appComponent.inject(this)
    }

    override fun onCreateViewHolder(view: ViewGroup, position: Int): CardViewHolder {
        return CardViewHolder(
            LayoutInflater
                .from(view.context)
                .inflate(R.layout.card_item, view, false)
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cardList[position])
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun updateCardList(cards: List<CardEntity>) {
        cardList = cards
        notifyDataSetChanged()
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(card: CardEntity) {
            with(itemView) {
                Picasso.get()
                    .load(card.imageUrlHiRes)
                    .into(cardItemImageView)

                changeFavoriteIconIfCardIsFavorite(card)

                cardItemImageView.setOnClickListener {
                    adapterCardView.replaceByDetailInformationFragmentFromAdapter(card)
                }

                favoriteIconImageView.setOnClickListener {
                    adapterCardView.updateFavoriteCardFromAdapter(card)
                    changeFavoriteIconIfCardIsFavorite(card)
                }
            }
        }

        private fun changeFavoriteIconIfCardIsFavorite(card: CardEntity) {
            if (favoriteCardsGatewayImpl.cardIsFavorite(card)) {
                itemView.favoriteIconImageView.setImageResource(R.drawable.ic_favorite_active)
            } else {
                itemView.favoriteIconImageView.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }
}