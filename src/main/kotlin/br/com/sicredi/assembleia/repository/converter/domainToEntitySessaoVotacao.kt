package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.repository.entity.SessaoVotacaoEntity

fun domainToEntitySessaoVotacao(sessaoVotacao: SessaoVotacao): SessaoVotacaoEntity {
    return SessaoVotacaoEntity(
        id = sessaoVotacao.id,
        pautaId = sessaoVotacao.pautaId,
        startDateTime = sessaoVotacao.startDateTime,
        endDateTime = sessaoVotacao.endDateTime,
        votacaoEncerrada = sessaoVotacao.votacaoEncerrada,
        pautaAprovada = sessaoVotacao.pautaAprovada,
        totalVotos = sessaoVotacao.totalVotos,
        totalVotosContrario = sessaoVotacao.totalVotosContrario,
        totalVotosFavoravel = sessaoVotacao.totalVotosFavoravel
    )
}
