package br.com.sicredi.assembleia.repository

import br.com.sicredi.assembleia.domain.model.SessaoVotacao
import br.com.sicredi.assembleia.domain.store.StoreSessaoVotacaoService
import br.com.sicredi.assembleia.repository.converter.domainToEntitySessaoVotacao
import br.com.sicredi.assembleia.repository.converter.entityToDomainSessaoVotacao
import org.springframework.stereotype.Service

@Service
class StoreSessaoVotacaoServiceImpl(private val repository: SessaoVotacaoRepository) : StoreSessaoVotacaoService {
    override fun open(sessaoVotacao: SessaoVotacao): SessaoVotacao {
        val entity = domainToEntitySessaoVotacao(sessaoVotacao)
        val savedSessao = repository.save(entity)
        return entityToDomainSessaoVotacao(savedSessao)
    }

    override fun getSessaoVotacao(sessaoVotacacaoId: Long): SessaoVotacao? {
        return repository.findById(sessaoVotacacaoId)
            .orElse(null)
            ?.let { entityToDomainSessaoVotacao(it) }
    }

    override fun update(sessaoVotacao: SessaoVotacao): SessaoVotacao {
        return open(sessaoVotacao)
    }
}
