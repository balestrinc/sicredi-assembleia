package br.com.sicredi.assembleia.repository.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sessao_votacao")
data class SessaoVotacaoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var pautaId: Long,

    var startDateTime: LocalDateTime?,
    var endDateTime: LocalDateTime?
)
