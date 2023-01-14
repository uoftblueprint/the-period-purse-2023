package com.example.theperiodpurse.data

interface userDAO {
    fun get(): User
    fun save(user: User): User
    fun update(user: User): User
    fun delete(): Unit
}