package br.com.sicredi.assembleia.api.converter

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.ws.model.CreatePautaRequest

fun wsToDomainPauta(ws: CreatePautaRequest): Pauta {
    return Pauta(
        title = ws.title,
        description = ws.description
    )
}
