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
    "ya29.a0AfB_byBEqMwX5KauKLXftLGK9XBMK45mRI10njjJfiiJ0q4cDsnITmtTVUCKC7bTH0Xg9DBJ0F-oLECaJNeGsrheZ4CW4kPDzLDsUy4soOldpe8JOIyZ87xSvDl97BtBTjcveVZfXvCyMCqTG6DOcFAx23i3OMdHH0YQDgaCgYKAX4SARASFQHsvYls1wwm8N5x_SUeG0FFDdppyw0173"
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
