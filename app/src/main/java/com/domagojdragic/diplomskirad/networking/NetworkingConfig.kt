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
    "ya29.a0AfB_byC6axnqYRF0H8WE1bpDApnOEDn7wSPFVnqMsJgjb3zFjCEdfkI9jNf26yoJJYOgcCpDrOV6od9A-Z6pUZkISj4_X3ZJnwE_96z4P1U_40kIFCQVsKg6Ct2SVomo8Vuoq5hgjucc9KHFTHMRsI3O81LVdwxohkr6HU8q9gaCgYKAX0SARASFQHsvYlsmEgAJrzKuhdJI0vJiSB7AQ0177"
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
