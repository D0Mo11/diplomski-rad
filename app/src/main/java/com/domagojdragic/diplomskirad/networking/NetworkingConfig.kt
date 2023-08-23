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
    "ya29.a0AfB_byAUTSURtXAYJHLhk3lVoo-NNENzenkDPPSE9b7RxXtEqg-7t12FQfw2DxOkekX0Hr8sFPvAX7ywh6ApzzBvgrseb4teEbHv0IChUQtnDfvVawGtdRtSz51XoMID-PjUNp0bM9k2h2wXf84oNRNnFwB1i39GP_RBeQaCgYKAR8SARASFQHsvYlsC7xC0GCR0WFO9IbwUBW_ng0173"
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
