package com.domagojdragic.diplomskirad.networking

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

val access_token =
    "ya29.a0AfB_byAUoCr4tiskIswZU-1HS1GXG1PDR7Rd1swyc7t2Dc189DWm8aHf-NPsNjS7K07ovkDS7qO7SEwxhJkHTomLZ0t1VY4bPyPGFX9jjJzYlg3GKb0OXq9UVuD2HGi7hIBXWRAyXByKBfiSUAvVs89WysNhakT6G1idr0r3aCgYKAU8SARASFQHsvYlsKcaLYFGMg5gjzsH2aaywTA0175"
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
}
