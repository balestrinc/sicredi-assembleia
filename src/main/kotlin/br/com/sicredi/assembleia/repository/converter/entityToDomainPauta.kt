package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.repository.entity.PautaEntity

fun entityToDomainPauta(pautaEntity: PautaEntity): Pauta {
    return Pauta(
        id = pautaEntity.id,
        title = pautaEntity.title,
        description = pautaEntity.description
    )
}
