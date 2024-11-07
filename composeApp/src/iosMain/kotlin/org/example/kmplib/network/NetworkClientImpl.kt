package org.example.kmplib.network

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.contentType
import io.ktor.http.ContentType
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import org.example.kmplib.models.RequestData

actual class NetworkClient {
    private val client = HttpClient {
        install(ContentNegotiation.toString()) {
            Json { prettyPrint = true }
        }
    }

    actual suspend fun sendRequest(requestData: RequestData): String {
        val response: HttpResponse = client.post("https://httpbin.org/post") {
            contentType(ContentType.Application.Json)
            setBody(requestData)
        }
        return response.bodyAsText()
    }
}
