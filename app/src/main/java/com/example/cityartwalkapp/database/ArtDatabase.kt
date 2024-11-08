package com.example.cityartwalkapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cityartwalkapp.ArtEntry


@Database(entities = [ArtEntry::class], version = 1, exportSchema = false)
@TypeConverters(ArtEntryTypeConverters::class)
abstract class ArtDatabase: RoomDatabase() {
    abstract fun artEntryDao(): ArtEntryDAO

    companion object{
        @Volatile
        private var INSTANCE: ArtDatabase? = null


        fun getDatabase(context: Context): ArtDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArtDatabase::class.java,
                    "art_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}