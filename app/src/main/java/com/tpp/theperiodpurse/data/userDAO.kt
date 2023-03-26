package com.tpp.theperiodpurse.data

import androidx.room.*

@Dao
interface UserDAO {
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun get(id: Int): User
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>
    @Query("UPDATE users SET symptomsToTrack = :symptoms WHERE id = :id")
    suspend fun updateSymptoms(id: Int, symptoms: List<Symptom>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: User)
    @Update
    suspend fun update(user: User)
    @Delete
    suspend fun delete(user: User)
}