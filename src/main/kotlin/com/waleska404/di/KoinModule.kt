package com.waleska404.di

import com.waleska404.repository.CharacterRepository
import com.waleska404.repository.CharacterRepositoryAlternative
import com.waleska404.repository.CharacterRepositoryImpl
import com.waleska404.repository.CharacterRepositoryImplAlternative
import org.koin.dsl.module

val koinModule = module {
    single<CharacterRepository> {
        CharacterRepositoryImpl()
    }
    single<CharacterRepositoryAlternative> {
        CharacterRepositoryImplAlternative()
    }
}