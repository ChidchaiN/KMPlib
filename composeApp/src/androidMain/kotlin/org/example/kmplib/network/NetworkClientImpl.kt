package org.example.kmplib.network

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.http.contentType
import io.ktor.http.ContentType
import kotlinx.serialization.json.Json
import org.example.kmplib.models.RequestData

class KtorNetworkClient : NetworkClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { prettyPrint = true })
        }
    }

    override suspend fun sendRequest(requestData: RequestData): String {
        val response = client.post("http://192.168.200.145:5000/insert-data") {
            contentType(ContentType.Application.Json)
            setBody(requestData)
        }
        return response.bodyAsText()
    }
}
