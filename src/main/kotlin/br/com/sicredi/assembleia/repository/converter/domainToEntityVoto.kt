package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.repository.entity.VotoEntity

fun domainToEntityVoto(voto: Voto): VotoEntity {
    return VotoEntity(
        pautaId = voto.pautaId,
        sessaoId = voto.sessaoId,
        votoOpcao = voto.votoOpcao,
        associadoCPF = voto.associadoCPF
    )
}
