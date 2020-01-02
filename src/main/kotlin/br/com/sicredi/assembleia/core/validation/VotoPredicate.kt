package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.core.exception.AssembeiaValidationException
import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.store.StoreVotoService
import org.springframework.stereotype.Component

@Component
class VotoPredicate(private val storeVotoService: StoreVotoService) {
    fun associadoDidNotVote(voto: Voto): Boolean {
        val vote = storeVotoService.getVotoAssocado(voto.sessaoId, voto.associadoCPF)
        return if (vote == null) true else throw AssembeiaValidationException("Associado já votou nesta sessão de votação")
    }
}
