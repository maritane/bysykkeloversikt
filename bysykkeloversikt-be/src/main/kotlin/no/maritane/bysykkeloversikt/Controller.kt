package no.maritane.bysykkeloversikt

import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.reactive.function.client.WebClientException

private val logger = KotlinLogging.logger {  }

@RestController
class Controller(val klient: BysykkelKlient) {
    @GetMapping("/stations")
    fun stations(): ResponseEntity<StationPayloadResponse> {
        println("Her blr det forespurt stasjoner")
        return ResponseEntity.ok(klient.getStationPayloads())
    }
}

@ControllerAdvice
class ExceptionHandler() {
    @ExceptionHandler
    fun handleException(exception: Exception, request: WebRequest): ResponseEntity<String> {
        logger.warn { "Kunne ikke besvare forespørsel ${request}" }
        if (exception is WebClientException)
            return ResponseEntity.badRequest().build()
        return ResponseEntity.internalServerError().build()
    }
}