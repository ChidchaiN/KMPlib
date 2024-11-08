package org.example.kmplib.network

import org.example.kmplib.models.RequestData

interface NetworkClient {
    suspend fun sendRequest(requestData: RequestData): String
}