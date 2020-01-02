package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.repository.entity.SessaoVotacaoEntity

fun entityToDomainSessaoVotacao(entity: SessaoVotacaoEntity): SessaoVotacao {
    return SessaoVotacao(
        id = entity.id,
        pautaId = entity.pautaId,
        startDateTime = entity.startDateTime,
        endDateTime = entity.endDateTime,
        votacaoEncerrada = entity.votacaoEncerrada,
        pautaAprovada = entity.pautaAprovada,
        totalVotos = entity.totalVotos,
        totalVotosContrario = entity.totalVotosContrario,
        totalVotosFavoravel = entity.totalVotosFavoravel
    )
}
