package br.com.sicredi.assembleia.domain.store

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import org.springframework.stereotype.Service

@Service
interface StoreSessaoVotacaoService {
    fun open(sessaoVotacao: SessaoVotacao): SessaoVotacao

    fun getSessaoVotacao(sessaoVotacacaoId: Long): SessaoVotacao?

    fun update(sessaoVotacao: SessaoVotacao): SessaoVotacao
}
