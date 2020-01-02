package br.com.sicredi.assembleia.repository

import br.com.sicredi.assembleia.domain.model.VotoOpcao
import br.com.sicredi.assembleia.repository.entity.VotoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VotoRepository : JpaRepository<VotoEntity, Long> {
    fun countBySessaoId(sessaoId: Long): Long

    fun countBySessaoIdAndAndVotoOpcao(sessaoId: Long, votoOpcao: VotoOpcao): Long
}
