package com.dicoding.submission3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserRoom : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoom? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoom {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoom::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserRoom::class.java, "favorite_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoriteUserRoom
        }
    }
}