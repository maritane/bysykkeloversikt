package no.maritane.bysykkeloversikt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.util.Arrays.asList

private val logger = KotlinLogging.logger { }

class BysykkelKlientTest {
    val restTemplate = mock(RestTemplate::class.java)
    val klient = BysykkelKlient(restTemplate)

    val id_regularStation = "2315"
    val id_notInstalled = "2309"
    val id_notRenting = "2308"
    val id_notReturning = "2307"
    val id_missingStation = "2306"
    val id_missingStatus = "2305"

    @BeforeEach
    fun setup() {
        val stations: StationResponse = jacksonObjectMapper().readValue(
            this::class.java.classLoader.getResource("stations.json"),
            StationResponse::class.java
        )
        val statuses: StationStatusResponse = jacksonObjectMapper().readValue(
            this::class.java.classLoader.getResource("statuses.json"),
            StationStatusResponse::class.java
        )

        whenever(restTemplate.getForEntity<StationResponse>("/station_information.json", StationResponse::class.java))
            .thenReturn(ResponseEntity(stations, HttpStatus.OK))
        whenever(
            restTemplate.getForEntity<StationStatusResponse>(
                "/station_status.json",
                StationStatusResponse::class.java
            )
        )
            .thenReturn(ResponseEntity(statuses, HttpStatus.OK))
    }

    @Test
    fun getStationPayloads() {
        val res = klient.getStationPayloads()
        val stations = res.data.stations
        // Dersom vi ikke får treff på både stations og statuses, så vises ikke stasjonen, så derfor forventer vi bare 4 resultater
        assertThat(stations).hasSize(4)

        assertThat(stations).containsExactlyInAnyOrderElementsOf(
            asList(
                // vanlig stasjon
                StationPayload(id_regularStation, "Rostockgata", "Rostockgata 5", 11, 9, 1635197386),
                // 'is_installed' = 0
                StationPayload(id_notInstalled, "Ulven Torg", "Ulvenveien 89", 0, 0, 1635197386),
                // 'is_renting' = 0
                StationPayload(id_notRenting, "Gunerius", "Storgata 33", 0, 6, 1635197386),
                // 'is_returning' = 0
                StationPayload(id_notReturning, "Domus Athletica", "Trimveien 4", 2, 0, 1635197386),
            )
        )
    }
}