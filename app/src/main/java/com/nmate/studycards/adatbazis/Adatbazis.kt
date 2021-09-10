package com.nmate.studycards.adatbazis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nmate.studycards.modellek.Kartya
import com.nmate.studycards.modellek.Tag
import com.nmate.studycards.modellek.TagKartyaKapcsolo

@Database(
    entities = [Kartya::class, Tag::class, TagKartyaKapcsolo::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Konverterek::class)
abstract class Adatbazis: RoomDatabase() {

    abstract val Dao: Dao

    companion object {
        @Volatile
        private var INSTANCE: Adatbazis? = null

        fun getInstance(context: Context): Adatbazis {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Adatbazis::class.java,
                        "adatbazis"
                    ).fallbackToDestructiveMigration().build()
                }
                INSTANCE = instance
                return instance
            }

        }
    }

}