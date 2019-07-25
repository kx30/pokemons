package ru.webant.gateway.gateways.network

import io.realm.Realm
import ru.webant.core.gateways.FavoriteCardsGateway
import ru.webant.core.models.CardEntity
import ru.webant.gateway.models.RealmCardEntity

class FavoriteCardsGatewayImpl(private val realm: Realm): FavoriteCardsGateway {

    override fun updateFavoriteCard(card: CardEntity) {
        realm.executeTransaction { realm ->
            if (!cardIsFavorite(card)) {
                val realmCard = realm
                    .where(RealmCardEntity::class.java)
                    .equalTo("id", card.id)
                    .findFirst()
                realmCard?.isFavorite = true
            } else {
                val realmCard = realm
                    .where(RealmCardEntity::class.java)
                    .equalTo("id", card.id)
                    .findFirst()
                realmCard?.isFavorite = false
            }
        }
    }

    override fun getFavoriteCards(page: Int): ArrayList<CardEntity> {
        val favoriteCardEntityList = ArrayList<CardEntity>()
        realm.executeTransaction { realm ->
            realm.where(RealmCardEntity::class.java)
                .equalTo("isFavorite", true)
                .between("cardId", page * 10, (page + 1) * 10)
                .limit(10)
                .findAll()
                .map { favoriteCardEntityList.add(it.toDomain()) }
        }
        return favoriteCardEntityList
    }

    override fun cardIsFavorite(card: CardEntity): Boolean {
        return realm.where(RealmCardEntity::class.java)
            .equalTo("id", card.id)
            .and()
            .equalTo("isFavorite", true)
            .findFirst() != null
    }
}