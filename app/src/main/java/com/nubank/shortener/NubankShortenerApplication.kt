package com.nubank.shortener

import android.app.Application
import android.content.pm.PackageManager
import com.nubank.shortener.network.di.networkModule
import com.nubank.shortener.observability.di.observabilityModule
import com.nubank.shortener.feature.shortener.presentation.di.shortenerModules
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NubankShortenerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        configureSentry()
        startKoin {
            androidContext(this@NubankShortenerApplication)
            modules(
                listOf(observabilityModule, networkModule) + shortenerModules,
            )
        }
    }

    private fun configureSentry() {
        val dsn = sentryDsnFromManifest()
        if (dsn.isBlank()) return

        SentryAndroid.init(this) { options ->
            options.dsn = dsn
            options.environment = if (BuildConfig.DEBUG) "debug" else "release"
            options.release = "${BuildConfig.APPLICATION_ID}@${BuildConfig.VERSION_NAME}+${BuildConfig.VERSION_CODE}"
            options.isDebug = false
            options.setDiagnosticLevel(SentryLevel.WARNING)
            options.isEnableAutoSessionTracking = true
            options.tracesSampleRate = if (BuildConfig.DEBUG) 1.0 else 0.1
        }
    }

    private fun sentryDsnFromManifest(): String {
        val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        return appInfo.metaData?.getString("io.sentry.dsn").orEmpty()
    }
}
