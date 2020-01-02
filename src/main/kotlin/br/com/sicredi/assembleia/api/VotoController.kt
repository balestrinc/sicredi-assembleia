package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.api.converter.wsToDomainVoto
import br.com.sicredi.assembleia.core.VotoService
import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.ws.model.VotoRequest
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import javax.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pauta/{pautaId}/sessao/{sessaoId}/voto")
class VotoController(private val votoService: VotoService) {

    @ApiOperation(value = "Registra voto para uma Sessão de Votação", response = Voto::class)
    @ApiResponses(
        ApiResponse(code = 200, message = "Voto na Sessão de Votação registrado com sucesso"),
        ApiResponse(code = 400, message = "A requisição é inválida ou não pode ser processada")
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addVoto(
        @PathVariable pautaId: Long,
        @PathVariable sessaoId: Long,
        @Valid @RequestBody requestBody: VotoRequest
    ): Voto { // todo create model to return
        return votoService.vote(wsToDomainVoto(pautaId, sessaoId, requestBody))
    }
}
