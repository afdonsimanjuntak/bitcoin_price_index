package io.afdon.bitcoinprice.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.afdon.bitcoinprice.presentation.main.MainFragment

@Module
interface FragmentModule {

    @Binds
    @ActivityScope
    fun bindFragmentFactory(appFragmentFactory: AppFragmentFactory) : FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(MainFragment::class)
    fun bindSearchFragment(fragment: MainFragment) : Fragment
}