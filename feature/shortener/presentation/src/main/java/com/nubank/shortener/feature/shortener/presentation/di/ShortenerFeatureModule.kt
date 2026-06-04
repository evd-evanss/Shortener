package com.nubank.shortener.feature.shortener.presentation.di

import com.nubank.shortener.feature.shortener.data.di.dataModule
import com.nubank.shortener.feature.shortener.domain.di.domainModule
import com.nubank.shortener.feature.shortener.presentation.screen.ShortenerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val shortenerPresentationModule = module {
    viewModel { ShortenerViewModel(get(), get()) }
}

val shortenerModules = listOf(
    dataModule,
    domainModule,
    shortenerPresentationModule,
)
