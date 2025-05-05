package com.android.smartstudy.di

import com.android.smartstudy.data.reporistory.SessionRepositoryImpl
import com.android.smartstudy.data.reporistory.SubjectRepositoryImpl
import com.android.smartstudy.data.reporistory.TaskRepositoryImpl
import com.android.smartstudy.domain.repository.SessionRepository
import com.android.smartstudy.domain.repository.SubjectRepository
import com.android.smartstudy.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsSubjectsRepository(impl: SubjectRepositoryImpl): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindsTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Singleton
    @Binds
    abstract fun bindsSessionRepository(impl: SessionRepositoryImpl): SessionRepository

}