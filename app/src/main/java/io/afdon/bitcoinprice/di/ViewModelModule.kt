package io.afdon.bitcoinprice.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.afdon.bitcoinprice.presentation.main.MainViewModel

@Module
interface ViewModelModule {

    @Binds
    @ActivityScope
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModelFactory(
        factory: MainViewModel.Factory
    ): AssistedViewModelFactory<out ViewModel>
}