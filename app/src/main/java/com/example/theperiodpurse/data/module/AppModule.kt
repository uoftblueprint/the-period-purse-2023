package com.example.theperiodpurse.data.module

import com.example.theperiodpurse.data.DateDAO
import com.example.theperiodpurse.data.DateRepository
import com.example.theperiodpurse.data.UserDAO
import com.example.theperiodpurse.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideUserRepository(userDAO: UserDAO): UserRepository {
        return UserRepository(userDAO)
    }

    @Singleton
    @Provides
    fun provideDateRepository(dateDAO: DateDAO): DateRepository {
        return DateRepository(dateDAO)
    }
}