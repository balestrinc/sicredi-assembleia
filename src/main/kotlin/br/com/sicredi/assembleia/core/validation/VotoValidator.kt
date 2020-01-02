package br.com.sicredi.assembleia.core.validation

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.store.StoreSessaoVotacaoService
import org.springframework.stereotype.Component

@Component
class VotoValidator(
    private val pautaPredicate: PautaPredicate,
    private val sessaoVotacaoPredicate: SessaoVotacaoPredicate,
    private val storeSessaoVotacaoService: StoreSessaoVotacaoService,
    private val associadoPredicate: AssociadoPredicate,
    private val votoPredicate: VotoPredicate
) {
    fun validate(voto: Voto): Boolean {
        val sessaoVotacao = storeSessaoVotacaoService.getSessaoVotacao(voto.sessaoId)
        return pautaPredicate.pautaExists(voto.pautaId) &&
                sessaoVotacaoPredicate.sessaoExists(sessaoVotacao, voto.sessaoId) &&
                sessaoVotacaoPredicate.isSessaoOpen(sessaoVotacao!!) &&
                votoPredicate.associadoDidNotVote(voto) &&
                associadoPredicate.cpfEnabledToVote(voto.associadoCPF)
    }
}
