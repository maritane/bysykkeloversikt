package no.maritane.bysykkeloversikt

import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import java.util.Arrays.asList

private val logger = KotlinLogging.logger { }

class BysykkelKlientIT {

    val klient = BysykkelKlient()

    @Test
    fun getStations() {

        val res = klient.getStations().data.stations
        assertThat(res).isNotEmpty
        assertThat(res).containsAnyElementsOf(
            asList(
                Station(
                    "1919", "Kværnerveien",
                    59.90591083488326,
                    10.778592132296495,
                    "Kværnerveien 5",
                    6
                )
            )
        )
    }

    @Test
    fun getStationStatuses() {
        val res = klient.getStationStatus().data.stations
        assertThat(res).isNotEmpty
        assertThat(res).anyMatch { it.station_id == "1919" && it.is_installed == 1 && it.is_returning == 1 && it.is_renting == 1 }
    }

    @Test
    fun getStationPayloads() {
        val res = klient.getStationPayloads()
        logger.info { res.data.stations }
    }
}