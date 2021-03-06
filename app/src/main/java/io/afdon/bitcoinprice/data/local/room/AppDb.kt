package io.afdon.bitcoinprice.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbBpiEntity::class], version = 6)
abstract class AppDb : RoomDatabase() {

    abstract fun bpiDao(): BpiDao
}