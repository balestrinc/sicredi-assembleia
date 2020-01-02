package br.com.sicredi.assembleia.api

import br.com.sicredi.assembleia.api.converter.wsToDomainSessaoVotacao
import br.com.sicredi.assembleia.core.SessaoVotacaoService
import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.ws.model.OpenSessaoVotacaoRequest
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pauta/{pautaId}/sessao")
class SessaoVotacaoController(private val sessaoVotacaoService: SessaoVotacaoService) {

    @ApiOperation(
        value = "Abre uma Sessão de Votação. Caso data de início e/ou " +
                "fim não sejam informados, o período da sessão será calculado com valor default",
        response = SessaoVotacao::class
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "A Sessão de Votação foi criada com sucesso"),
        ApiResponse(code = 400, message = "A requisição é inválida ou não pode ser processada")
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createSessaoVotacao(@PathVariable pautaId: Long, @RequestBody requestBody: OpenSessaoVotacaoRequest): SessaoVotacao {
        return sessaoVotacaoService.open(wsToDomainSessaoVotacao(pautaId, requestBody))
    }

    @ApiOperation(value = "Atualiza a apuração da Sessão de Votação", response = SessaoVotacao::class)
    @ApiResponses(
        ApiResponse(code = 200, message = "Apuração da Sessão de Votação realizada com sucesso"),
        ApiResponse(code = 400, message = "A requisição é inválida ou não pode ser processada")
    )
    @PutMapping("/{sessaoId}")
    fun updateVotacaoResultado(@PathVariable pautaId: Long, @PathVariable sessaoId: Long): SessaoVotacao {
        return sessaoVotacaoService.updateVotacaoResultado(pautaId, sessaoId)
    }
}
