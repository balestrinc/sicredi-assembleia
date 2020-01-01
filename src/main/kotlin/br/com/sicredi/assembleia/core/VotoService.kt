package br.com.sicredi.assembleia.core

import br.com.sicredi.assembleia.core.validation.VotoValidator
import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.store.StoreVotoService
import org.springframework.stereotype.Service

@Service
class VotoService(
    private val storeVotoService: StoreVotoService,
    private val validator: VotoValidator
) {
    fun vote(voto: Voto): Voto {
        validator.validate(voto)
        return storeVotoService.save(voto)
    }
}
