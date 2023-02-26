package com.example.theperiodpurse.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities=[User::class, Date::class], version = 11, exportSchema = false)
@TypeConverters(SymptomConverter::class, DateConverter::class, DaysConverter::class)
abstract class ApplicationRoomDatabase: RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun dateDAO(): DateDAO
    companion object {
        @Volatile
        private var INSTANCE: ApplicationRoomDatabase? = null
        fun getDatabase(context: Context): ApplicationRoomDatabase {
            // Exposed for now to see if SQLCipher encryption works
            val passphrase: ByteArray = SQLiteDatabase.getBytes(
                "ltqZHr/glhTLg4iAD1xpOg5fh9mivIBWG2pZ6tw6qlw=".toCharArray()
            )
            val factory = SupportFactory(passphrase)
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationRoomDatabase::class.java,
                    "user_database_encrypted_test"
                )
                    .openHelperFactory(factory)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}