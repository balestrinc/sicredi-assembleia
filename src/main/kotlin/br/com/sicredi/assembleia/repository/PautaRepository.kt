package br.com.sicredi.assembleia.repository

import br.com.sicredi.assembleia.repository.entity.PautaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PautaRepository : JpaRepository<PautaEntity, Long>
