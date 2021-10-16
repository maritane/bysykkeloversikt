package no.maritane.bysykkeloversikt

import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import java.util.Arrays.asList

val logger = KotlinLogging.logger {  }
internal class BysykkelKlientTest {

    val klient = BysykkelKlient()

    @Test
    fun getStations() {

        val res = klient.getStations().data.stations;
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
    fun getStationPayloads() {
        val res = klient.getStationPayloads()
        logger.info { res.data.stations }
    }
}