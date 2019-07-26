package ru.webant.core.models

import java.io.Serializable

data class CardEntity (
    val id: String,
    val name: String,
    val imageUrlHiRes: String,
    val supertype: String,
    val subtype: String,
    val hp: String,
    val rarity: String?,
    val attacks: List<TypeOfAttacksEntity>,
    val isFavorite: Boolean = false
): Serializable