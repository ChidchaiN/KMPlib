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

actual class NetworkClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { prettyPrint = true })
        }
    }

    actual suspend fun sendRequest(requestData: RequestData): String {
        val response = client.post("https://1l0z1syceg.execute-api.ap-southeast-1.amazonaws.com/uat/v1/gateway/v1/initial/register") {
            contentType(ContentType.Application.Json)
            setBody(requestData)
        }
        return response.bodyAsText()
    }
}
