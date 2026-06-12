package com.shortener

import android.app.Application
import android.content.pm.PackageManager
import com.shortener.feature.shortener.impl.di.shortenerImplModule
import com.shortener.network.di.networkModule
import com.shortener.observability.di.observabilityModule
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShortenerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        configureSentry()
        startKoin {
            androidContext(this@ShortenerApp)
            modules(
                listOf(observabilityModule, networkModule) + shortenerImplModule,
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
