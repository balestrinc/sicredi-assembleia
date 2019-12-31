package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.store.StorePautaService
import org.springframework.stereotype.Component

@Component
class PautaPredicate(private val storePautaService: StorePautaService) {

    val pautaExists: (Long) -> Boolean = { pautaId ->
        storePautaService.getPauta(pautaId)?.let { true } ?: throw AssembeiaValidationException("Pauta $pautaId does not exist")
    }
}
