package io.afdon.bitcoinprice.di

import androidx.fragment.app.FragmentFactory
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [
    FragmentModule::class,
    ViewModelModule::class
])
interface ActivitySubcomponent {

    @Subcomponent.Factory
    interface Factory {

        fun create() : ActivitySubcomponent
    }

    fun getFragmentFactory() : FragmentFactory
}