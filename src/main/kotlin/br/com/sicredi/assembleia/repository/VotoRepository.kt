package br.com.sicredi.assembleia.repository

import br.com.sicredi.assembleia.repository.entity.VotoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VotoRepository : JpaRepository<VotoEntity, Long>
