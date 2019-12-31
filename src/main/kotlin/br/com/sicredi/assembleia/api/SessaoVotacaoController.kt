package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.api.converter.wsToDomainSessaoVotacao
import br.com.sicredi.assembleia.core.SessaoVotacaoService
import br.com.sicredi.assembleia.ws.model.OpenSessaoVotacaoRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pauta")
class SessaoVotacaoController(private val sessaoVotacaoService: SessaoVotacaoService) {

    @PostMapping("/{pautaId}/sessao", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createSessaoVotacao(@PathVariable pautaId: Long, @RequestBody requestBody: OpenSessaoVotacaoRequest): Boolean {
        return sessaoVotacaoService.open(wsToDomainSessaoVotacao(pautaId, requestBody))
    }
}
