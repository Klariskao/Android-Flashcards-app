package com.example.flashcards.di

import android.content.Context
import com.example.flashcards.data.local.VocabularyDao
import com.example.flashcards.data.local.VocabularyDatabase
import com.example.flashcards.repository.VocabularyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context) = VocabularyDatabase.getDatabase(context)

    @Provides
    fun provideDao(database: VocabularyDatabase) = database.vocabularyDao()

    @Provides
    fun provideRepository(dao: VocabularyDao) = VocabularyRepository(dao)
}