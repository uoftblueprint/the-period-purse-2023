package com.tpp.theperiodpurse.data

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DateRepository(private val dateDAO: DateDAO) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addDate(date: Date, context: Context) {
        coroutineScope.launch (Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                val instance = ApplicationRoomDatabase.getDatabase(context)
                instance.openHelper.readableDatabase
                instance.openHelper.writableDatabase
                dateDAO.save(date)
            }
        }
    }

    fun getAllDates(): Flow<List<Date>> {
        return dateDAO.getDates()
    }

    fun deleteDate(date: Date, context: Context) {
        coroutineScope.launch (Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                val instance = ApplicationRoomDatabase.getDatabase(context)
                instance.openHelper.readableDatabase
                instance.openHelper.writableDatabase
                dateDAO.delete(date)
            }
        }
    }

    fun deleteManyDates(dates: List<Long>, context: Context) {

        coroutineScope.launch (Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                val instance = ApplicationRoomDatabase.getDatabase(context)
                instance.openHelper.readableDatabase
                instance.openHelper.writableDatabase
                dateDAO.deleteMany(dates)
            }
        }
    }
}