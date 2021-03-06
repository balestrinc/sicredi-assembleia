package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import org.springframework.stereotype.Component

@Component
class OpenSessaoVotacaoValidator(
    val pautaPredicate: PautaPredicate,
    val sessaoVotacaoPredicate: SessaoVotacaoPredicate
) {
    fun validate(sessaoVotacao: SessaoVotacao): Boolean {
        return pautaPredicate.pautaExists(sessaoVotacao.pautaId) &&
                sessaoVotacaoPredicate.isPeriodValid(sessaoVotacao)
    }
}
