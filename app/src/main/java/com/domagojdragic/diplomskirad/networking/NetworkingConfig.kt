package com.domagojdragic.diplomskirad.networking

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val access_token =
    "ya29.a0AfB_byBM2SLQ5igLx6hOmonPArgwzMUGw5CyvH3C3LEpx6tddAmZG521c7qh3ZZJziL0FMUlOLf7ywTkjfQIXVUewNSwX1fLpkIqf801DS4JtQUOu6DvUwFjE-01z6-3iVvYd3dYt2nVIKxW-Hnrx-8zDF-1noWwa249ZGtPaCgYKAQsSARASFQHsvYlsovzjHp0kmshOWbHU21nceg0175"
val refresh_token = "1//04oKMke2rfz3YCgYIARAAGAQSNwF-L9Ir5ncRGEkqIGSLRQJyUGWWhB5OQCu8PoQ5c66_irlN1YAEBkAu9BW5SFW9xqofDIjxNzQ"

val OAuth2Client: HttpClient = HttpClient(CIO) {
    install(Auth) {
        bearer {
            loadTokens {
                BearerTokens(
                    accessToken = access_token,
                    refreshToken = refresh_token,
                )
            }
        }

    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("HTTP", message)
            }
        }
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
        )
    }
    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status", "${response.status.value}")
        }
    }
}
