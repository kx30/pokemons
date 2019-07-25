package ru.webant.core.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TypeOfAttacksEntity(
    val cost: List<String>,
    val name: String,
    val damage: String,
    @SerializedName("convertedEnergyCost") val energyCost: Int
): Serializable