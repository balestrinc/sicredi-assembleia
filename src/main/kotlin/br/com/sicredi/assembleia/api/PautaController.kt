package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.api.converter.wsToDomainPauta
import br.com.sicredi.assembleia.core.PautaService
import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.ws.model.CreatePautaRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pauta")
class PautaController(private val pautaService: PautaService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPauta(@RequestBody requestBody: CreatePautaRequest): Pauta {
        return pautaService.createPauta(wsToDomainPauta(requestBody))
    }
}
