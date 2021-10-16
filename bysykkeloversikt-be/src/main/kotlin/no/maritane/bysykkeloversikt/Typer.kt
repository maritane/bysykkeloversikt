package no.maritane.bysykkeloversikt

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

// TODO: generics, men krever da også å sette JavaType til objectmapper.readValueMethod
//  Mulig dette er vanskelig å få gjort i kotlin.. får se om vi får sjekket det ut
@JsonIgnoreProperties(ignoreUnknown = true)
data class StationResponse(
    val last_updated: Long,
    val ttl: Number,
    val data: StationList
)

data class StationList(
    val stations: Collection<Station>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StationStatusResponse(
    val last_updated: Long,
    val ttl: Number,
    val data: StationStatusList
)

data class StationStatusList(
    val stations: Collection<StationStatus>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StationPayloadResponse(
    val last_updated: Long,
    val ttl: Number,
    val data: StationPayloadList
)

data class StationPayloadList(
    val stations: Collection<StationPayload>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Station(
    val station_id: String,
    val name: String,
    val lat: Double,
    val lon: Double,
    val address: String,
    val capacity: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StationStatus(
    val station_id: String,
    val is_installed: Int,
    val is_renting: Int,
    val num_bikes_available: Int,
    val num_docks_available: Int,
    val last_reported: Int,
    val is_returning: Int,
)

data class StationPayload(
    val station_id: String,
    val name: String,
    val address: String,
    val num_bikes_available: Int,
    val num_docks_available: Int,
    val last_reported: Int
)