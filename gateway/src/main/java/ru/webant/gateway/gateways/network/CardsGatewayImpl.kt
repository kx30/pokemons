package ru.webant.gateway.gateways.network

import io.reactivex.Single
import io.realm.Realm
import ru.webant.core.gateways.CardsGateway
import ru.webant.core.models.CardEntity
import ru.webant.core.models.JsonCardsEntity
import ru.webant.gateway.models.RealmCardEntity

class CardsGatewayImpl(private val api: Api, private val realm: Realm) : CardsGateway {

    override fun getCards(page: Int): Single<JsonCardsEntity> = api.getCards(page = page)

    override fun saveCard(card: CardEntity) {
        realm.executeTransaction { realm ->
            if (realm.where(RealmCardEntity::class.java).equalTo("id", card.id).findFirst() == null) {
                val realmCard = RealmCardEntity.fromDomain(card)
                setCardId(realmCard)
                realm.copyToRealm(realmCard)
            }
        }
    }

    private fun setCardId(card: RealmCardEntity) {
        val currentCardId = realm.where(RealmCardEntity::class.java).max("cardId")
        var nextId = 0
        currentCardId?.let { nextId = currentCardId.toInt() + 1 }
        card.cardId = nextId
    }

    override fun getCardsFromDatabase(page: Int): List<CardEntity> {
        val cardEntityList = ArrayList<CardEntity>()
        realm.executeTransaction { realm ->
            realm.where(RealmCardEntity::class.java)
                .between("cardId", page * 10, (page + 1) * 10)
                .limit(10)
                .findAll()
                .map { cardEntityList.add(it.toDomain()) }
        }
        return cardEntityList
    }
}
