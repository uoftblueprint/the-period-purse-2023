package com.tpp.theperiodpurse.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities=[User::class, Date::class], version = 7, exportSchema = false)
@TypeConverters(
    SymptomConverter::class,
    DateConverter::class,
    DaysConverter::class,
    DurationConverter::class
)
abstract class ApplicationRoomDatabase: RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun dateDAO(): DateDAO
    companion object {
        @Volatile
        private var INSTANCE: ApplicationRoomDatabase? = null
        fun getDatabase(context: Context): ApplicationRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationRoomDatabase::class.java,
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
        fun clearDatabase(context: Context): Boolean {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ApplicationRoomDatabase::class.java,
                "user_database"
            )
                .fallbackToDestructiveMigration()
                .build()

            instance.clearAllTables()
            return true
        }
    }
}