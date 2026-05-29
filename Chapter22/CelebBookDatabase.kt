package com.example.celebbook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class CelebBookDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: CelebBookDatabase? = null

        fun getDatabase(context: Context): CelebBookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CelebBookDatabase::class.java,
                    "celeb_book_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
