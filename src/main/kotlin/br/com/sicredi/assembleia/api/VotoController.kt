package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.api.converter.wsToDomainVoto
import br.com.sicredi.assembleia.core.VotoService
import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.ws.model.VotoRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pauta/{pautaId}/sessao/{sessaoId}/voto")
class VotoController(private val votoService: VotoService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createSessaoVotacao(
        @PathVariable pautaId: Long,
        @PathVariable sessaoId: Long,
        @RequestBody requestBody: VotoRequest
    ): Voto { // todo create model to return
        return votoService.vote(wsToDomainVoto(pautaId, sessaoId, requestBody))
    }
}
