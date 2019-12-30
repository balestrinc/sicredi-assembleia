package br.com.sicredi.assembleia.repository.converter

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.repository.entity.PautaEntity

fun domainToEntityPauta(pauta: Pauta): PautaEntity {
    return PautaEntity(
        title = pauta.title,
        description = pauta.description
    )
}
