package com.codefrnd.mvvmapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codefrnd.mvvmapp.data.db.entities.User

@Database(entities = [User::class], version = 1)
abstract class AppDB : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDB? = null

        /** WE DO NOT CREATE TWO INSTANCES */
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                // ALWAYS PASS APPLICATION CONTEXT
                context.applicationContext,
                AppDB::class.java,
                "MyDB.db"
            ).build()
    }
}