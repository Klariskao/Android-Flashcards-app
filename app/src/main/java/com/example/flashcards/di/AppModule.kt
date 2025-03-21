package com.example.flashcards.di

import com.example.flashcards.data.local.VocabularyDatabase
import com.example.flashcards.repository.VocabularyRepository
import com.example.flashcards.ui.flashcards.FlashcardViewModel
import com.example.flashcards.ui.vocabulary.VocabularyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule =
    module {
        single { VocabularyDatabase.getDatabase(androidContext()).vocabularyDao() }
        single { VocabularyRepository(get()) }
        viewModel { FlashcardViewModel(get()) }
        viewModel { VocabularyViewModel(get()) }
    }
