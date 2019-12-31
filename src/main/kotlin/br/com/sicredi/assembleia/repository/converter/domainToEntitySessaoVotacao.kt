package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.repository.entity.SessaoVotacaoEntity

fun domainToEntitySessaoVotacao(sessaoVotacao: SessaoVotacao): SessaoVotacaoEntity {
    return SessaoVotacaoEntity(
        pautaId = sessaoVotacao.pautaId,
        startDateTime = sessaoVotacao.startDateTime,
        endDateTime = sessaoVotacao.endDateTime
    )
}
