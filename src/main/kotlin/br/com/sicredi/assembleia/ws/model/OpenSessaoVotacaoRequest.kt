package br.com.sicredi.assembleia.ws.model

import java.time.LocalDateTime

data class OpenSessaoVotacaoRequest(
    val startDateTime: LocalDateTime?,
    val endDateTime: LocalDateTime?
)
