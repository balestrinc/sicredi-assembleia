package br.com.sicredi.assembleia.core

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.store.StoreVotoService
import org.springframework.stereotype.Service

@Service
class VotoService(private val storeVotoService: StoreVotoService) {
    fun vote(voto: Voto): Voto {
        return storeVotoService.save(voto)
    }
}
