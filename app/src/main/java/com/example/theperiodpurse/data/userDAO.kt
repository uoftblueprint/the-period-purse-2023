package com.example.theperiodpurse.data

import javax.persistence.EntityManager
import javax.persistence.Persistence

class UserDAO {
    private val em: EntityManager
    private val emf =
        Persistence.createEntityManagerFactory("persistenceUnit")

    init {
        em = emf.createEntityManager()
    }

    fun create(user: User) {
        em.transaction.begin()
        em.persist(user)
        em.transaction.commit()
    }

    fun findById(id: Long): User? {
        return em.find(User::class.java, id)
    }

    fun update(user: User) {
        em.transaction.begin()
        em.merge(user)
        em.transaction.commit()
    }

    fun delete(user: User) {
        em.transaction.begin()
        em.remove(user)
        em.transaction.commit()
    }

    fun destroy() {
        em.close()
        emf.close()
    }
}