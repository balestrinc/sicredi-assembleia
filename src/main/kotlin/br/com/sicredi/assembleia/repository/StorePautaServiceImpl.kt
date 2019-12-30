package br.com.sicredi.assembleia.repository

import br.com.sicredi.assembleia.domain.model.Pauta
import br.com.sicredi.assembleia.domain.store.StorePautaService
import br.com.sicredi.assembleia.repository.converter.domainToEntityPauta
import br.com.sicredi.assembleia.repository.converter.entityToDomainPauta
import org.springframework.stereotype.Service

@Service
class StorePautaServiceImpl(private val repository: PautaRepository) : StorePautaService {
    override fun createPauta(pauta: Pauta): Pauta {
        val entity = domainToEntityPauta(pauta)
        val createdPauta = repository.save(entity)
        return entityToDomainPauta(createdPauta)
    }
}
