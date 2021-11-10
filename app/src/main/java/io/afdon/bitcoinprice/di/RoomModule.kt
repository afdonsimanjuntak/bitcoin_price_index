package io.afdon.bitcoinprice.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.afdon.bitcoinprice.data.local.room.AppDb
import io.afdon.bitcoinprice.data.local.room.BpiDao
import javax.inject.Singleton

@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDb(context: Context) : AppDb =
        Room.databaseBuilder(context, AppDb::class.java, "app_db").build()

    @Singleton
    @Provides
    fun provideBpiDao(appDb: AppDb) : BpiDao = appDb.bpiDao()
}