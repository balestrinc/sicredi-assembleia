package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.repository.entity.VotoEntity

fun entityToDomainVoto(entity: VotoEntity): Voto {
    return Voto(
        id = entity.id,
        pautaId = entity.pautaId,
        sessaoId = entity.sessaoId,
        associadoCPF = entity.associadoCPF,
        votoOpcao = entity.votoOpcao
    )
}
