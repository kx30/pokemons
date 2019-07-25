package ru.webant.pokemons.di

import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesRealm(): Realm = Realm.getDefaultInstance()
}