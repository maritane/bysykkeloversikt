package no.maritane.bysykkeloversikt

import mu.KotlinLogging
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.time.Instant

private val logger = KotlinLogging.logger { }

@Component
class BysykkelKlient(
    val klient: RestTemplate = RestTemplateBuilder()
        .defaultHeader("Client-Identifier", "maritanestad-bysykkeloversikt")
        .rootUri("https://gbfs.urbansharing.com/oslobysykkel.no")
        .build()
) {
    val sekunderFoerNesteLastingAvStasjoner = 60 * 10

    // Lagrer stasjoner her, så vi ikke trenger å hente nye stasjoner hvert eneste gang apiet blir kallt
    var stations: Map<String, Station> = HashMap()
    var lastUpdated: Long = 0

    fun getStations(): StationResponse {
        val res = klient.getForEntity<StationResponse>("/station_information.json")
        return res.body ?: throw Exception("Feil ved henting av stasjoner, statuskode: ${res.statusCode}")
    }

    fun getStationStatus(): StationStatusResponse {
        val res = klient.getForEntity<StationStatusResponse>("/station_status.json")
        return res.body ?: throw Exception("Feil ved henting av stasjonsstatuser, statuskode: ${res.statusCode}")
    }

    fun getStationPayloads(): StationPayloadResponse {
        val stationStatuses: Collection<StationStatus> = getStationStatus().data.stations

        if (lastUpdated < (Instant.now().epochSecond - sekunderFoerNesteLastingAvStasjoner)) {
            logger.info { "Laster stasjoner. Last updated: $lastUpdated, ${Instant.now().epochSecond - sekunderFoerNesteLastingAvStasjoner}" }
            val response: StationResponse = getStations()

            stations = response.data.stations
                .associate { Pair(it.station_id, it) }
            lastUpdated = Instant.now().epochSecond
        }

        return StationPayloadResponse(
            lastUpdated,
            10,
            StationPayloadList(stationStatuses.map { toStationPayload(it) }.filterNotNull().sortedBy { it.name })
        )
    }

    private fun toStationPayload(status: StationStatus): StationPayload? {
        val station = stations.get(status.station_id)
        if (station == null) {
            logger.error { "Kunne ikke finne stasjon med id=${status.station_id}" }
            return null
        }
        return StationPayload(
            status.station_id,
            station.name,
            station.address,
            if (status.is_installed == 1 && status.is_renting == 1) status.num_bikes_available else 0,
            if (status.is_installed == 1 && status.is_returning == 1) status.num_docks_available else 0,
            status.last_reported
        )
    }


}