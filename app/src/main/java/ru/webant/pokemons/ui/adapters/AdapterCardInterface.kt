package ru.webant.pokemons.ui.adapters

import ru.webant.core.models.CardEntity

interface AdapterCardInterface {

    fun replaceByDetailInformationFragmentFromAdapter(card: CardEntity)
    fun updateFavoriteCardFromAdapter(card: CardEntity)
}