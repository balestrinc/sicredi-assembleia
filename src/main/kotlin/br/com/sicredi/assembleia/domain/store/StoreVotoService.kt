package br.com.sicredi.assembleia.domain.store

import br.com.sicredi.assembleia.domain.model.Voto
import org.springframework.stereotype.Service

@Service
interface StoreVotoService {
    fun save(voto: Voto): Voto
}
