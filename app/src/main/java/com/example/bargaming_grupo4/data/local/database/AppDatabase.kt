package com.example.bargaming_grupo4.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.bargaming_grupo4.data.local.user.UserDao
import com.example.bargaming_grupo4.data.local.user.UsersEntity

// Base de datos room
@Database(
    entities = [UsersEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "bargaming_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }

    }
}