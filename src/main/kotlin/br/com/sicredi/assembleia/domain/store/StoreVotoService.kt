package br.com.sicredi.assembleia.domain.store

import br.com.sicredi.assembleia.domain.model.Voto
import br.com.sicredi.assembleia.domain.model.VotoOpcao
import org.springframework.stereotype.Service

@Service
interface StoreVotoService {
    fun save(voto: Voto): Voto

    fun getTotalVotos(sessaoVotacaoId: Long): Long

    fun getTotalVotos(sessaoVotacaoId: Long, votoOpcao: VotoOpcao): Long
}
