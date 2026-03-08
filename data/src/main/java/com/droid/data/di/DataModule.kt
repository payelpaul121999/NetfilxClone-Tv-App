package com.droid.data.di

import com.droid.data.api.Api
import com.droid.data.api.ApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import org.koin.dsl.module

val dataModule = module {
    single<Api> { ApiImpl(get()) }
    single<HttpClient> {
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }

        }
    }
}
