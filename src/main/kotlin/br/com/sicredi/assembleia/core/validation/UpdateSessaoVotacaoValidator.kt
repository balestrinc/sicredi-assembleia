package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import org.springframework.stereotype.Component

@Component
class UpdateSessaoVotacaoValidator(
    val pautaPredicate: PautaPredicate,
    val sessaoVotacaoPredicate: SessaoVotacaoPredicate
) {
    fun validate(pautaId: Long, sessaoVotacaoId: Long, sessaoVotacao: SessaoVotacao?): Boolean {
        return pautaPredicate.pautaExists(pautaId) &&
                sessaoVotacaoPredicate.sessaoExists(sessaoVotacao, sessaoVotacaoId) &&
                sessaoVotacaoPredicate.votacaoNotResulted(sessaoVotacao!!)
    }
}
