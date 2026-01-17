package com.seenu.dev.android.january26.holiday_movie_collection.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seenu.dev.android.january26.holiday_movie_collection.data.entity.Collection
import com.seenu.dev.android.january26.holiday_movie_collection.data.entity.CollectionWithMovieCrossRef
import com.seenu.dev.android.january26.holiday_movie_collection.data.entity.Movie

@Database(
    entities = [
        Movie::class,
        Collection::class,
        CollectionWithMovieCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieCollectionDatabase : RoomDatabase() {


    companion object {
        private var _dbInstance: MovieCollectionDatabase? = null

        fun getInstance(context: Context): MovieCollectionDatabase {
            if (_dbInstance == null) {
                synchronized(MovieCollectionDatabase::class) {
                    _dbInstance = Room.databaseBuilder(
                        context,
                        MovieCollectionDatabase::class.java,
                        "holiday_movie_collection_database.db"
                    ).build()
                }
            }
            return _dbInstance!!
        }

        fun getInstance() = _dbInstance!!
    }

    abstract val dao: MovieCollectionDao

}