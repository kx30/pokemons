package ru.webant.core.models

import java.io.Serializable

data class JsonCardsEntity (
    val cards: List<CardEntity>
): Serializable