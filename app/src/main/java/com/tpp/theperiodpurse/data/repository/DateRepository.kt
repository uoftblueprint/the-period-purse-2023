package com.tpp.theperiodpurse.data.repository

import com.tpp.theperiodpurse.data.entity.Date
import com.tpp.theperiodpurse.data.DateDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DateRepository(private val dateDAO: DateDAO) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addDate(date: Date) {
        coroutineScope.launch (Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                dateDAO.save(date)
            }
        }
    }

    fun getAllDates(): Flow<List<Date>> {
        return dateDAO.getDates()
    }

    fun deleteDate(date: Date) {
        coroutineScope.launch (Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                dateDAO.delete(date)
            }
        }
    }

    fun deleteManyDates(dates: List<Long>) {

        coroutineScope.launch (Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                dateDAO.deleteMany(dates)
            }
        }
    }
}