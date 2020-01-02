package br.com.sicredi.assembleia.core

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.domain.store.StoreVotoService
import org.springframework.stereotype.Component

@Component
class VotacaoFactory(private val storeVotoService: StoreVotoService, private val clock: Clock) {
    fun buildVotacao(sessaoVotacao: SessaoVotacao): SessaoVotacao {
        val totalVotos = storeVotoService.getTotalVotos(sessaoVotacao.id)
        val totalVotosFavoravel = storeVotoService.getTotalVotos(sessaoVotacao.id, VotoOpcao.SIM)
        val totalVotosContrario = totalVotos - totalVotosFavoravel
        sessaoVotacao.totalVotos = totalVotos
        sessaoVotacao.totalVotosFavoravel = totalVotosFavoravel
        sessaoVotacao.totalVotosContrario = totalVotos - totalVotosFavoravel

        val isVotacaoEncerrada = clock.now().isAfter(sessaoVotacao.endDateTime)
        val isPautaAprovada = isVotacaoEncerrada && (totalVotosFavoravel > totalVotosContrario)

        sessaoVotacao.votacaoEncerrada = isVotacaoEncerrada
        sessaoVotacao.pautaAprovada = isPautaAprovada

        return sessaoVotacao
    }
}
