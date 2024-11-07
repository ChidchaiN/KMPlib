package org.example.kmplib.models


import kotlinx.serialization.Serializable

@Serializable
data class RequestData(
    val provider_id: String,
    val merchant_id: String,
    val shop_id: String,
    val pos_id: String,
    val invite_code: String,
    val client_type: String, // AGENT or EDC
    val device_sn: String, // FOR EDC
    val device_type: String,
    val device_name: String,
    val location: Location,
    val edc_config: EdcConfig
)

@Serializable
data class Location(
    val lat: String,
    val long: String
)

@Serializable
data class EdcConfig(
    val config: String // Example placeholder field
)
