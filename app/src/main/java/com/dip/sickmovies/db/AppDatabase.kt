package com.dip.sickmovies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dip.sickmovies.models.Movie
import com.dip.sickmovies.models.NowPlayingMovie
import com.dip.sickmovies.models.PopularMovie
import com.dip.sickmovies.models.TopRatedMovie
import com.dip.sickmovies.utils.Utils.DATABASE_NAME

@Database(
    entities = [Movie::class, PopularMovie::class, TopRatedMovie::class, NowPlayingMovie::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: createDatabase(context).also { instance = it }
            }
        }

        private fun createDatabase(context: Context): AppDatabase {

            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()

        }

    }

}