package org.example.kmplib

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.kmplib.models.EdcConfig
import org.example.kmplib.models.Location
import org.example.kmplib.models.RequestData
import org.example.kmplib.network.KtorNetworkClient

class MainActivity : ComponentActivity() {
    private val networkClient = KtorNetworkClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    @Composable
    fun App() {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Click the button to send a request")

            Button(
                onClick = {
                    // Sample data
                    val sampleRequestData = RequestData(
                        provider_id = "provider123",
                        merchant_id = "merchant456",
                        shop_id = "shop789",
                        pos_id = "pos101112",
                        invite_code = "invite1234",
                        client_type = "EDC",
                        device_sn = "deviceSN001",
                        device_type = "typeA",
                        device_name = "deviceNameX",
                        location = Location(
                            lat = "40.7128",
                            long = "-74.0060"
                        ),
                        edc_config = EdcConfig(
                            config = "sampleConfig"
                        )
                    )

                    // Send request in a coroutine
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            Log.d("NetworkRequest", "Sending request with data: $sampleRequestData") // Log request data
                            val response = networkClient.sendRequest(sampleRequestData)
                            Log.d("NetworkRequest", "Response received: $response") // Log the response
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Request succeeded!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } catch (e: Exception) {
                            val errorMessage = when (e) {
                                is ClientRequestException -> "Client error: ${e.message}"
                                is ServerResponseException -> "Server error: ${e.message}"
                                is IOException -> "Network error: ${e.message}"
                                is UnresolvedAddressException -> "Unable to resolve address: ${e.message}"
                                else -> "An unexpected error occurred: ${e.message}"
                            }
                            Log.e("NetworkRequest", errorMessage) // Log error details
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Send Request")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppAndroidPreview() {
    App()
}
