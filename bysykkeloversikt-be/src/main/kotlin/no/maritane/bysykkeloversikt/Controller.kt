package no.maritane.bysykkeloversikt

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

private val logger = KotlinLogging.logger {  }

@RestController
class Controller(val klient: BysykkelKlient) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stations", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun stations(): ResponseEntity<StationPayloadResponse> {
        logger.info { "Henter stasjoner" }
        return ResponseEntity.ok(klient.getStationPayloads())
    }
}

@ControllerAdvice
class ExceptionHandler() {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    fun handleException(exception: Exception, request: WebRequest): ResponseEntity<String> {
        logger.warn { "Kunne ikke besvare forespørsel ${request}" }
        return ResponseEntity.internalServerError().build()
    }
}