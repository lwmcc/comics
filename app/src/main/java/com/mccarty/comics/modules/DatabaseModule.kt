package com.mccarty.comics.modules

import android.content.Context
import com.mccarty.comics.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Provides
    fun provideImageDao(appDatabase: AppDatabase) = appDatabase.characterDao()
}