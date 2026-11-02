package com.vk.directop.grandstore.detailcard.di

import com.vk.directop.grandstore.detailcard.data.repository.GamesRepositoryImpl
import com.vk.directop.grandstore.detailcard.domain.repository.GamesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailCardModule {

    @Binds
    @Singleton
    abstract fun bindGamesRepository(impl: GamesRepositoryImpl): GamesRepository
}
