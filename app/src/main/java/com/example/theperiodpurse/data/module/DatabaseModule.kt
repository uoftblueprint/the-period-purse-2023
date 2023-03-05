package com.example.theperiodpurse.data.module

import android.content.Context
import com.example.theperiodpurse.data.ApplicationRoomDatabase
import com.example.theperiodpurse.data.DateDAO
import com.example.theperiodpurse.data.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private object DatabaseModule {
    @Provides
    @Singleton
    fun provideUserDao(appDatabase: ApplicationRoomDatabase): UserDAO {
        return appDatabase.userDAO()
    }

    @Provides
    @Singleton
    fun provideDateDao(appDatabase: ApplicationRoomDatabase): DateDAO {
        return appDatabase.dateDAO()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): ApplicationRoomDatabase {
        return ApplicationRoomDatabase.getDatabase(context)
    }
}