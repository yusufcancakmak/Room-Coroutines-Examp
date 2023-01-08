package com.yusufcancakmak.roomdatabase_withcoroutine

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao():UserDao


    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: kotlin.synchronized(LOCK) {
          instance ?: buildDatabaase(context).also {
              instance=it
          }
        }

        private fun buildDatabaase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app-database"
        ).build()

    }


}