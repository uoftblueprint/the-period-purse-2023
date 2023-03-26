package com.tpp.theperiodpurse.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DateRepository(private val dateDAO: DateDAO) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addDate(date: Date) {
        coroutineScope.launch (Dispatchers.IO) {
            dateDAO.save(date)
        }
    }

    fun getAllDates(): Flow<List<Date>> {
        return dateDAO.getDates()
    }
}