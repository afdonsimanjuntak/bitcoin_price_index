package io.afdon.bitcoinprice

import android.app.Application
import io.afdon.bitcoinprice.di.DaggerAppComponent

class MainApp : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(this.applicationContext)
    }
}