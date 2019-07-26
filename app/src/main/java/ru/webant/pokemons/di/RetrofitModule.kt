package ru.webant.pokemons.di

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun providesGson(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providesCallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun providesClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            when (response.code()) {
                400 -> Log.e("Error", "The request was invalid")
                403 -> Log.e("Error", "The client did not have permission to access the requested resource")
                404 -> Log.e("Error", "Page not found")
                405 -> Log.e("Error", "The HTTP method in the request was not supported by the resource. For example, the DELETE method cannot be used with the Agent API.")
                500 -> Log.e("Error", "The request was not completed due to an internal error on the server side")
                503 -> Log.e("Error", "The server was unavailable.")
            }
            response
        }
        .addNetworkInterceptor(HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

    @Provides
    @Singleton
    fun providesRetrofit(
        gson: GsonConverterFactory,
        callAdapterFactory: RxJava2CallAdapterFactory,
        client: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.pokemontcg.io")
        .addCallAdapterFactory(callAdapterFactory)
        .addConverterFactory(gson)
        .client(client)
        .build()

}