package br.com.sicredi.assembleia.domain.store

import br.com.sicredi.assembleia.domain.model.Pauta
import org.springframework.stereotype.Service

@Service
interface StorePautaService {
    fun createPauta(pauta: Pauta): Pauta

    fun getPauta(pautaId: Long): Pauta?
}
