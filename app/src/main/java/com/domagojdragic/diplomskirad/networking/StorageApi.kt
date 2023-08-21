package com.domagojdragic.diplomskirad.networking

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface StorageApi {

    suspend fun fetchFireImages(): ImageListResponse
}

private const val API_URL = "https://storage.googleapis.com/storage/v1/b/diplomskirad-28a99.appspot.com/o"

class StorageApiImpl(private val client: HttpClient) : StorageApi {

    override suspend fun fetchFireImages(): ImageListResponse = client.get(API_URL).body()

}
