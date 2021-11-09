package io.afdon.bitcoinprice.di

import dagger.Module
import dagger.Provides
import io.afdon.bitcoinprice.data.remote.CoindeskApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideCoindeskApi(retrofit: Retrofit) : CoindeskApi =
        retrofit.create(CoindeskApi::class.java)
}