package com.tpp.theperiodpurse.data

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDAO: UserDAO) {
    val isOnboarded: MutableLiveData<Boolean?> = MutableLiveData(null)
    val isDeleted: MutableLiveData<Boolean?> = MutableLiveData(null)
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

    suspend fun getUser(id: Int): User {
        return userDAO.get(id)
    }
    suspend fun isEmpty(): Boolean {
        return userDAO.getUsers().isEmpty()
    }

    suspend fun isOnboarded() {
        isOnboarded.postValue(userDAO.getUsers().isNotEmpty())
    }
    fun isDeleted(context: Context) {
        isOnboarded.postValue(ApplicationRoomDatabase.clearDatabase(context))
    }

}