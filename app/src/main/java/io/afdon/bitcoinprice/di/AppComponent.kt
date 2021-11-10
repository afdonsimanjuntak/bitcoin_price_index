package io.afdon.bitcoinprice.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    SubcomponentModule::class,
    RepositoryModule::class,
    ApiModule::class,
    RetrofitModule::class,
    RoomModule::class
])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {

         fun create(@BindsInstance  context: Context) : AppComponent
    }

    fun getActivitySubcomponentFactory() : ActivitySubcomponent.Factory
}