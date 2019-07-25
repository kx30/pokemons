package ru.webant.pokemons.di

import dagger.Module
import dagger.Provides
import io.realm.Realm
import retrofit2.Retrofit
import ru.webant.core.gateways.CardsGateway
import ru.webant.core.gateways.FavoriteCardsGateway
import ru.webant.gateway.gateways.network.Api
import ru.webant.gateway.gateways.network.CardsGatewayImpl
import ru.webant.gateway.gateways.network.FavoriteCardsGatewayImpl
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class, DatabaseModule::class])
class GatewayModule {

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun providesCards(api: Api, realm: Realm): CardsGateway = CardsGatewayImpl(api, realm)

    @Provides
    @Singleton
    fun providesFavoriteCards(realm: Realm): FavoriteCardsGateway = FavoriteCardsGatewayImpl(realm)
}