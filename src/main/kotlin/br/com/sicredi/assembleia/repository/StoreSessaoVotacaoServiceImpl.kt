package br.com.sicredi.assembleia.repository

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.store.StoreSessaoVotacaoService
import br.com.sicredi.assembleia.repository.converter.domainToEntitySessaoVotacao
import org.springframework.stereotype.Service

@Service
class StoreSessaoVotacaoServiceImpl(private val repository: SessaoVotacaoRepository) : StoreSessaoVotacaoService {
    override fun open(sessaoVotacao: SessaoVotacao): Boolean {
        val entity = domainToEntitySessaoVotacao(sessaoVotacao)
        repository.save(entity)
        return true
    }
}
