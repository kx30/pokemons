package ru.webant.core.gateways

import io.reactivex.Single
import ru.webant.core.models.CardEntity
import ru.webant.core.models.JsonCardsEntity

interface CardsGateway {

    fun getCards(page: Int): Single<JsonCardsEntity>
    fun getCardsFromDatabase(page: Int): List<CardEntity>
    fun saveCard(card: CardEntity)
}