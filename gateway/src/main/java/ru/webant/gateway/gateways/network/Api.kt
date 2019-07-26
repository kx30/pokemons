package ru.webant.gateway.gateways.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.webant.core.models.JsonCardsEntity

interface Api {

    @GET("v1/cards")
    fun getCards(
        @Query("supertype") supertype: String = "pokemon",
        @Query("pageSize") limit: Int = 10,
        @Query("page") page: Int
    ): Single<JsonCardsEntity>


    @GET("v1/cards")
    fun searchCards(
        @Query ("supertype") supertype: String = "pokemon",
        @Query("name") name: String,
        @Query("pageSize") limit: Int = 30
    ): Single<JsonCardsEntity>
}