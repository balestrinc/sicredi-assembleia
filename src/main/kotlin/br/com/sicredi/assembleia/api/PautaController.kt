package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.api.converter.wsToDomainPauta
import br.com.sicredi.assembleia.core.PautaService
import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.ws.model.CreatePautaRequest
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pauta")
class PautaController(private val pautaService: PautaService) {

    @ApiOperation(value = "Cria uma Pauta", response = Pauta::class)
    @ApiResponses(
        ApiResponse(code = 200, message = "A Pauta foi criada com sucesso"),
        ApiResponse(code = 400, message = "A requisição é inválida ou não pode ser processada")
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPauta(@RequestBody requestBody: CreatePautaRequest): Pauta {
        return pautaService.createPauta(wsToDomainPauta(requestBody))
    }
}
