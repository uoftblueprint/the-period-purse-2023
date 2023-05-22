package com.tpp.theperiodpurse.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tpp.theperiodpurse.data.entity.Date
import com.tpp.theperiodpurse.data.entity.User
import com.tpp.theperiodpurse.data.helper.DateConverter
import com.tpp.theperiodpurse.data.helper.DaysConverter
import com.tpp.theperiodpurse.data.helper.DurationConverter
import com.tpp.theperiodpurse.data.helper.SymptomConverter
import java.io.File

@Database(entities=[User::class, Date::class], version = 7, exportSchema = true)
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
                val path = context.getDatabasePath("user_database.db").path
                val databaseFile = File(path)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationRoomDatabase::class.java,
                    databaseFile.absolutePath
                )
                    .addCallback(getCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                instance.openHelper.readableDatabase
                instance.openHelper.writableDatabase
                INSTANCE = instance
                return instance
            }
        }

        fun getCallback(): Callback {
            return object : Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    db.execSQL("CREATE TEMP TABLE room_table_modification_log(table_id INTEGER PRIMARY KEY, invalidated INTEGER NOT NULL DEFAULT 0)")
                }
            }
        }

    }
}