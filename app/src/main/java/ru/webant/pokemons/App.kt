package ru.webant.pokemons

import android.app.Application
import io.realm.Realm
import ru.webant.pokemons.di.AppComponent
import ru.webant.pokemons.di.DaggerAppComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        appComponent = DaggerAppComponent.create()
    }


    companion object {
        lateinit var appComponent: AppComponent
    }
}