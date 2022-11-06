package ru.vdnh.android.di

import android.content.Context
import ru.vdnh.android.data.repository.CartRepositoryImpl
import ru.vdnh.android.data.repository.HomeRepositoryImpl
import ru.vdnh.android.data.repository.LoginRepositoryImpl
import ru.vdnh.android.data.repository.UserDataRepositoryImpl
import ru.vdnh.android.domain.repository.CartRepository
import ru.vdnh.android.domain.repository.HomeRepository
import ru.vdnh.android.domain.repository.LoginRepository
import ru.vdnh.android.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        @ApplicationContext context: Context
    ): LoginRepository = LoginRepositoryImpl(context = context)

    @Provides
    @Singleton
    fun providesHomeRepository(): HomeRepository = HomeRepositoryImpl()

    @Provides
    @Singleton
    fun providesUserDataRepository(
        @ApplicationContext context: Context
    ): UserDataRepository = UserDataRepositoryImpl(context = context)

    @Provides
    @Singleton
    fun providesCartRepository(): CartRepository = CartRepositoryImpl()

}
