package br.com.sicredi.assembleia.repository

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.store.StoreVotoService
import br.com.sicredi.assembleia.repository.converter.domainToEntityVoto
import br.com.sicredi.assembleia.repository.converter.entityToDomainVoto
import org.springframework.stereotype.Service

@Service
class StoreVotoServiceImpl(private val votoRepository: VotoRepository) : StoreVotoService {
    override fun save(voto: Voto): Voto {
        val entity = domainToEntityVoto(voto)
        val savedVoto = votoRepository.save(entity)
        return entityToDomainVoto(savedVoto)
    }
}
