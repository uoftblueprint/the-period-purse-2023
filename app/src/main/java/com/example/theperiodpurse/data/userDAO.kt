package com.example.theperiodpurse.data

import androidx.room.*

@Dao
interface userDAO {
    @Query("SELECT * FROM user where id = 1")
    fun get(): User
    @Query("SELECT * FROM user")
    fun getUsers(): List<User>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: User)
    @Update
    suspend fun update(user: User)
    @Delete
    suspend fun delete(user: User)
}