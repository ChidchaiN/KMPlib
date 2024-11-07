package org.example.kmplib.network

import org.example.kmplib.models.RequestData

expect class NetworkClient {
    suspend fun sendRequest(requestData: RequestData): String
}