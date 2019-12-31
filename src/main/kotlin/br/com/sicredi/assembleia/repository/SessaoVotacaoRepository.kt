package br.com.sicredi.assembleia.repository

import br.com.sicredi.assembleia.repository.entity.SessaoVotacaoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessaoVotacaoRepository : JpaRepository<SessaoVotacaoEntity, Long>
