package ru.webant.core.gateways

import ru.webant.core.models.CardEntity

interface FavoriteCardsGateway {

    fun getFavoriteCards(page: Int): ArrayList<CardEntity>
    fun updateFavoriteCard(card: CardEntity)
    fun cardIsFavorite(card: CardEntity): Boolean
}