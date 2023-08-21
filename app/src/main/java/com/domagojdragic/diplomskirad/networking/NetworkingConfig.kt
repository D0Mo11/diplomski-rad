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
    "ya29.a0AfB_byC1Nqlzhz9jovmuajkGcwVZaoIuRwg-zNaUVOaNlrBmBuGMDdnKi6ina8zy3xKGpF12AFnWTJreUevWTZK-EO7nbJ5ZRTPc5AryuA6e8NuNPezIEgMOzOFtXdmjQeZq5o7yYnBuqfjxxnbHAfZHCqztA8E_PV54181maCgYKAbkSARASFQHsvYlsP_TKvX6OKnLhiO7c_uhBJA0175"
val refresh_token = "1//0424xagH0_vGLCgYIARAAGAQSNwF-L9IrjVw6oFUndFAAGtimDTPxEn0h_iCPmJuvR26FIyVEhIHAWZxnszPhUzMYujn1KoV-0IA"

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
