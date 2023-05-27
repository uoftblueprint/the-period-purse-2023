package com.tpp.theperiodpurse.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.tpp.theperiodpurse.data.ApplicationRoomDatabase
import com.tpp.theperiodpurse.data.model.Symptom
import com.tpp.theperiodpurse.data.entity.User
import com.tpp.theperiodpurse.data.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDAO: UserDAO) {
    val isOnboarded: MutableLiveData<Boolean?> = MutableLiveData(null)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addUser(user: User) {
        coroutineScope.launch (Dispatchers.IO) {
            userDAO.save(user)
        }
    }

    fun setSymptoms(symptoms: List<Symptom>) {
        coroutineScope.launch (Dispatchers.IO) {
            val jsonSymptoms = Gson().toJson(symptoms);
            if (jsonSymptoms != null) {
                userDAO.updateSymptoms(symptoms = jsonSymptoms, id = 1)
            }
        }
    }

    fun setReminders(allowReminders: Boolean) {
        coroutineScope.launch (Dispatchers.IO) {
            userDAO.updateReminders(id = 1, allowReminders)
        }
    }

    fun setReminderTime(time: String) {
        coroutineScope.launch (Dispatchers.IO) {
            userDAO.updateReminderTime(id = 1, time)
        }
    }

    fun setReminderFreq(freq: String) {
        coroutineScope.launch (Dispatchers.IO) {
            userDAO.updateReminderFreq(id = 1, freq)
        }
    }

    suspend fun getUser(id: Int, context: Context): User {
        return ApplicationRoomDatabase.getDatabase(context).userDAO().get(id)
    }
    suspend fun isEmpty(context: Context): Boolean {
        return ApplicationRoomDatabase.getDatabase(context).userDAO().getUsers().isEmpty()
    }

    suspend fun isOnboarded() {
        isOnboarded.postValue(userDAO.getUsers().isNotEmpty())
    }

}