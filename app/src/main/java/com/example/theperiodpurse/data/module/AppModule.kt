package com.example.theperiodpurse.data.module

import com.example.theperiodpurse.data.UserRepository
import com.example.theperiodpurse.data.userDAO
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
    fun provideUserRepository(userDAO: userDAO): UserRepository {
        return UserRepository(userDAO)
    }
}