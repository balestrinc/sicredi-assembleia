package br.com.sicredi.assembleia.core

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.domain.store.StorePautaService
import org.springframework.stereotype.Service

@Service
class PautaService(private val storePautaService: StorePautaService) {

    fun createPauta(pauta: Pauta): Pauta {
        return storePautaService.createPauta(pauta)
    }
}
