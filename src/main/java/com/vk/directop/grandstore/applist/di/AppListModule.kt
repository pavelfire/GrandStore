package com.vk.directop.grandstore.applist.di

import com.vk.directop.grandstore.applist.data.repository.GamesRepositoryImpl
import com.vk.directop.grandstore.applist.domain.repository.GamesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppListModule {

    @Binds
    @Singleton
    abstract fun bindGamesRepository(impl: GamesRepositoryImpl): GamesRepository
}
