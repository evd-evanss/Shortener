package com.shortener.feature.shortener.impl.di

import com.shortener.feature.shortener.impl.ui.screen.ShortenerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val shortenerUiModule = module {
    viewModel { ShortenerViewModel(get(), get()) }
}

val shortenerImplModule = listOf(
    shortenerRepositoryModule,
    shortenerUseCaseModule,
    shortenerUiModule,
)
