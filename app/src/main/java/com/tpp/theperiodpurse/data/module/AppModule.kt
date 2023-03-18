package com.tpp.theperiodpurse.data.module

import com.tpp.theperiodpurse.data.DateDAO
import com.tpp.theperiodpurse.data.DateRepository
import com.tpp.theperiodpurse.data.UserDAO
import com.tpp.theperiodpurse.data.UserRepository
import com.tpp.theperiodpurse.ui.onboarding.OnboardViewModel
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

    @Singleton
    @Provides
    fun provideOnboardViewModel(userRepository: UserRepository, dateRepository: DateRepository):
            OnboardViewModel { return OnboardViewModel(userRepository, dateRepository) }
}