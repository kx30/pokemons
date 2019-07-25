package ru.webant.gateway.models

import io.realm.RealmList
import io.realm.RealmObject
import ru.webant.core.models.TypeOfAttacksEntity

open class RealmTypeOfAttacksEntity : RealmObject() {

    var cost = RealmList<String>()
    var name: String = ""
    var damage: String = ""
    var energyCost: Int = 0


    fun toDomain(): TypeOfAttacksEntity {
        return TypeOfAttacksEntity(
            cost = this.cost,
            name = this.name,
            damage = this.damage,
            energyCost = this.energyCost
        )
    }

    companion object {

        fun fromDomain(typeOfAttacksEntity: TypeOfAttacksEntity): RealmTypeOfAttacksEntity {
            return RealmTypeOfAttacksEntity().apply {
                typeOfAttacksEntity.cost.forEach { cost ->
                    this.cost.add(cost)
                }
                name = typeOfAttacksEntity.name
                damage = typeOfAttacksEntity.damage
                energyCost = typeOfAttacksEntity.energyCost
            }
        }
    }
}