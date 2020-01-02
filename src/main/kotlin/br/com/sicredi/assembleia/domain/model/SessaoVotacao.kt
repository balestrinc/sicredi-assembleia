package br.com.sicredi.assembleia.domain.model

import java.time.LocalDateTime

data class SessaoVotacao(
    val id: Long = 0,
    val pautaId: Long,
    val startDateTime: LocalDateTime?,
    val endDateTime: LocalDateTime?,

    var votacaoEncerrada: Boolean = false,
    var pautaAprovada: Boolean = false,
    var totalVotos: Long = 0,
    var totalVotosFavoravel: Long = 0,
    var totalVotosContrario: Long = 0
)
