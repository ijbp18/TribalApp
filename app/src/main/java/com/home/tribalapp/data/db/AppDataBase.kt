package com.home.tribalapp.data.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.home.tribalapp.util.Constants.DATABASE_NAME

@Database(entities = [FavoriteDTO::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun favoriteDAO(): FavoriteDAO

    companion object {

        @Volatile private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .build()
        }

    }
}