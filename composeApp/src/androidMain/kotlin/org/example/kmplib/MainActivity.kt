package org.example.kmplib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Toast
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.withContext
import java.io.IOException
import org.example.kmplib.models.EdcConfig
import org.example.kmplib.models.Location
import org.example.kmplib.models.RequestData
import org.example.kmplib.network.NetworkClient

class MainActivity : ComponentActivity() {
    private val networkClient = NetworkClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        // Send the request
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = networkClient.sendRequest(sampleRequestData)
                println("Response: $response") // Log the response

                // Switch to the Main dispatcher to update the UI if needed
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Request succeeded!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ClientRequestException) { // 4xx responses
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Client error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: ServerResponseException) { // 5xx responses
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Server error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) { // Network or timeout error
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Network error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: UnresolvedAddressException) { // DNS resolution error
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Unable to resolve address: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) { // Other unforeseen errors
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "An unexpected error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}