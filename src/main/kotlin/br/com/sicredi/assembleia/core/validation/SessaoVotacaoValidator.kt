package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import org.springframework.stereotype.Component

@Component
class SessaoVotacaoValidator(val pautaPredicate: PautaPredicate) {
    fun validate(sessaoVotacao: SessaoVotacao): Boolean {
        return pautaPredicate.pautaExists(sessaoVotacao.pautaId)
    }
}
