package br.com.sicredi.assembleia.api.converter

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.ws.model.OpenSessaoVotacaoRequest

fun wsToDomainSessaoVotacao(pautaId: Long, ws: OpenSessaoVotacaoRequest): SessaoVotacao {
    return SessaoVotacao(
        pautaId = pautaId,
        startDateTime = ws.startDateTime,
        endDateTime = ws.endDateTime
    )
}
