package ru.webant.gateway.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.webant.core.models.CardEntity
import ru.webant.core.models.TypeOfAttacksEntity

open class RealmCardEntity : RealmObject() {

    @PrimaryKey
    var cardId: Int = 0
    var id: String = ""
    var name: String = ""
    var imageUrlHiRes: String = ""
    var supertype: String = ""
    var subtype: String = ""
    var hp: String = ""
    var rarity: String? = null
    var attacks = RealmList<RealmTypeOfAttacksEntity>()
    var isFavorite: Boolean = false


    fun toDomain(): CardEntity {
        val cardAttack = ArrayList<TypeOfAttacksEntity>()
        this.attacks.forEach {
            cardAttack.add(it.toDomain())
        }
        return CardEntity(
            id = this.id,
            name = this.name,
            imageUrlHiRes = this.imageUrlHiRes,
            supertype = this.supertype,
            subtype = this.subtype,
            hp = this.hp,
            rarity = this.rarity,
            attacks = cardAttack,
            isFavorite = this.isFavorite
        )
    }


    companion object {

        fun fromDomain(card: CardEntity): RealmCardEntity {
            return RealmCardEntity().apply {
                id = card.id

                name = card.name
                imageUrlHiRes = card.imageUrlHiRes
                supertype = card.supertype
                subtype = card.subtype
                card.attacks.forEach {
                    attacks.add(RealmTypeOfAttacksEntity.fromDomain(it))
                }
                hp = card.hp
                rarity = card.rarity
                isFavorite = card.isFavorite
            }
        }
    }
}